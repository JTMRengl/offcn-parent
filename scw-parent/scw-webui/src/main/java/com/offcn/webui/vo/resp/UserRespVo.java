package com.offcn.webui.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class UserRespVo implements Serializable {

    @ApiModelProperty("访问令牌,每次访问需要携带认证")
    private String accessToken;// 令牌， 存在redis中


    private String loginacct; //存储手机号
    private String username;
    private String email;
    private String authstatus;
    private String usertype;
    private String realname;
    private String cardnum;
    private String accttype;

}
