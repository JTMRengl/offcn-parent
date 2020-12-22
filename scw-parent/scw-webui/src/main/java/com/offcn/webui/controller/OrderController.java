package com.offcn.webui.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.TOrder;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    //保存订单
    //@ResponseBody
    @RequestMapping("/order/save")
    public String OrderPay(OrderFormInfoSubmitVo vo, HttpSession session){

        //1.验证登录状态
        UserRespVo userResponseVo = (UserRespVo) session.getAttribute("sessionMember");
        if (userResponseVo == null){
            return "redirect:/login";
        }
        String accessToken = userResponseVo.getAccessToken();
        vo.setAccessToken(accessToken);



        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirmSession");
        if (confirmVo == null){
            return "redirect:/login";
        }


        vo.setProjectid(confirmVo.getProjectId());
        vo.setReturnid(confirmVo.getId());
        vo.setRtncount(confirmVo.getNum());



        AppResponse<TOrder> order = orderServiceFeign.createOrder(vo);//调用远程,生成订单




        TOrder data = order.getData();

        //下单成功，打印相关信息待处理
        String orderName = confirmVo.getProjectName();
        System.out.println("orderNum:"+data.getOrdernum());
        System.out.println("money:"+data.getMoney());
        System.out.println("orderName:"+orderName);
        System.out.println("Remark:"+vo.getRemark());

        return "/member/minecrowdfunding";
        // return  "redirect:"+result;
    }

}
