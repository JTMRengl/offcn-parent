package com.offcn.webui.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class DispathcherController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;
    
    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/")
    public String toIndex(Model model){

        //查询项目列表(先从redis中,没有的话再查远程微服务调用并设置到redis中)
        List<ProjectVo> data  = (List<ProjectVo>) redisTemplate.opsForValue().get( "projectStr" );
        if(data == null){
            AppResponse<List<ProjectVo>> all = projectServiceFeign.all();
            data = all.getData();
            redisTemplate.opsForValue().set( "projectStr", data, 1, TimeUnit.HOURS );
        }
        model.addAttribute( "projectList", data );
        return "index";
    }

    @PostMapping("/doLogin")
    public String doLogin(String loginacct, String password, HttpSession session){


        AppResponse<UserRespVo> appResponse = memberServiceFeign.login( loginacct, password );
        UserRespVo userRespVo = appResponse.getData();

        //打日志  ？
        log.info( "登录账号:{},密码:{}" ,loginacct, password );
        log.info("登录用户:{}", userRespVo);


        //判断登录用户是否为null
        if(userRespVo == null){
            return "redirect:/login.html";
        }
        //登录成功
        session.setAttribute( "sessionMember" , userRespVo);

        //往哪里返,判断用户是否之前有请求url
        String preUrl = (String) session.getAttribute( "preUrl" );
        if(preUrl == null){//首页
            return "redirect:/";
        }else{
            return "redirect:/"+preUrl;
        }
    }
}
