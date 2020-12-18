package com.offcn.order.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.service.impl.ProjectServiceFeignException;
import com.offcn.order.vo.resp.TReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SCW-PROJECT",fallback = ProjectServiceFeignException.class)//ip+port,
public interface ProjectServiceFeign {
    @GetMapping("/project/details/returns/{projectid}")
    public AppResponse<List<TReturn>> getProjectReturns(@PathVariable("projectid") Integer projectid);
}
