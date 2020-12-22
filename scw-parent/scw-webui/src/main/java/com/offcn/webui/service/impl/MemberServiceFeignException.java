package com.offcn.webui.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MemberServiceFeignException implements MemberServiceFeign {
    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【登录】");
        return fail;

    }

    @Override
    public AppResponse<UserRespVo> findUser(Integer id) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【获取用户信息】");
        return fail;
    }


    @Override
    public AppResponse<List<UserAddressVo>> address(String accessToken) {
        AppResponse<List<UserAddressVo>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【查询地址列表】");
        return fail;
    }

}
