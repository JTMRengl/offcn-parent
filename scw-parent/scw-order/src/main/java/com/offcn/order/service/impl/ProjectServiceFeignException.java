package com.offcn.order.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.resp.TReturn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<TReturn>> getProjectReturns(Integer projectid) {

        AppResponse<List<TReturn>> fail = AppResponse.fail( null );
        fail.setMsg( "熔断降级:调用远程项目失败" );
        return fail;
    }
}
