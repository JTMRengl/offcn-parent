package com.offcn.user.controller;

import com.offcn.user.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试的swagger")
public class HelloController {

    @ApiOperation("测试方法hello")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true)
    })
    @GetMapping("/hello")
    public String hello(String name){
        return "OK:"+name;
    }

    @ApiOperation("保存用户")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),@ApiImplicitParam(name="email",value="电子邮件")
    })
    @PostMapping("/user")
    public User save(String name, String email){
        User user  = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }

}
