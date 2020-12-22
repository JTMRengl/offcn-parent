package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.MemberServiceFeignException;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SCW-USER", configuration = FeignConfig.class, fallback =  MemberServiceFeignException.class)
public interface MemberServiceFeign {

    @PostMapping("/user/login")
    public AppResponse<UserRespVo> login(@RequestParam("username") String loginacct, @RequestParam("password") String password);

    //根据用户编号，获取用户基本信息
    @GetMapping("/user/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id);


    @GetMapping("/user/info/address")
    public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);

}
