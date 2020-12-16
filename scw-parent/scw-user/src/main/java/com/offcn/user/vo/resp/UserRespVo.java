package com.offcn.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录成功以后返回的信息
 */
@ApiModel
@Data
public class UserRespVo {

    @ApiModelProperty("访问令牌，请妥善保管，以后每次请求都要带上")
    private String accessToken;//访问令牌
    private String loginacct; //存储手机号
    private String username;
    private String email;
    private String authstatus;
    private String usertype;
    private String realname;
    private String cardnum;
    private String accttype;

}
