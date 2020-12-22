package com.offcn.webui.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private MemberServiceFeign memberServiceFeign;


    @RequestMapping("/projectInfo")
    public String projectInfo(Model model, Integer id, HttpSession session){

        AppResponse<ProjectDetailVo> projectDetailVoAppResponse = projectServiceFeign.detailsInfo( id );
        ProjectDetailVo data = projectDetailVoAppResponse.getData();

        model.addAttribute( "DetailVo", data );
        session.setAttribute( "DetailVo" , data);
        return "/project/project";
    }



    @RequestMapping("/confirm/returns/{projectId}/{returnId}")
    public String returnInfo(@PathVariable("projectId") Integer projectId,@PathVariable("returnId") Integer returnId,Model model,HttpSession session){
        //从session获取项目详细信息
        ProjectDetailVo projectDetailVo= (ProjectDetailVo) session.getAttribute("DetailVo");
        //根据项目回报编号，获取项目回报信息
        AppResponse<ReturnPayConfirmVo> appResponse = projectServiceFeign.returnInfo(returnId);
        //获取项目回报信息
        ReturnPayConfirmVo returnPayConfirmVo = appResponse.getData();

        //设置项目回报的项目id
        returnPayConfirmVo.setProjectId(projectId);
        //设置项目回报的项目名称
        returnPayConfirmVo.setProjectName(projectDetailVo.getName());
        //根据项目发起方id，获取项目发起方名称
        AppResponse<UserRespVo> memberServiceAppResponse = memberServiceFeign.findUser(projectDetailVo.getMemberid());
        UserRespVo userRespVo = memberServiceAppResponse.getData();
        //设置发起方名称
        returnPayConfirmVo.setMemberName(userRespVo.getRealname());
        //添加项目回报信息到session
        session.setAttribute("returnConfirm",returnPayConfirmVo);
        //添加项目回报信息到Model
        model.addAttribute("returnConfirm",returnPayConfirmVo);

        return "project/pay-step-1";
    }



    /**
     * 跳转支付确认页面
     * @param num
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/confirm/order/{num}")
    public String confirmOrder(@PathVariable("num") Integer num,Model model, HttpSession session){
        //1. 查询用户是否已登录
        UserRespVo data = (UserRespVo) session.getAttribute("sessionMember");
        //用户未登录或登录超时,转发到登录页面
        if (data == null){
            //用户登录成功后需要再跳转回当前页面
            session.setAttribute("preUrl","project/confirm/order/"+num);
            return "redirect:/login.html";
        }
        String accessToken = data.getAccessToken();
        //2. 根据用户accessToken查询用户的收货地址列表
        AppResponse<List<UserAddressVo>> addressResponse = memberServiceFeign.address(accessToken);
        List<UserAddressVo> addressVoList = addressResponse.getData();
        //3. 将地址存入request域中
        model.addAttribute("addresses",addressVoList);
        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirm");
        confirmVo.setNum(num);
        //涉及到金额，先在代码中计算，避免精确度缺失
        confirmVo.setTotalPrice(new BigDecimal(num * confirmVo.getSupportmoney() + confirmVo.getFreight()));
        session.setAttribute("returnConfirmSession",confirmVo);
        //4. 转发到paystep2页面
        return "project/pay-step-2";
    }

}
