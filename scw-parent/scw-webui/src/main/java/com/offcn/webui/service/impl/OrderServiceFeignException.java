package com.offcn.webui.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.TOrder;
import com.offcn.webui.vo.resp.UserAddressVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceFeignException implements OrderServiceFeign {
    @Override
    public AppResponse<TOrder> createOrder(OrderFormInfoSubmitVo vo) {
        AppResponse<TOrder> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【生成订单】");
        return fail;
    }

}
