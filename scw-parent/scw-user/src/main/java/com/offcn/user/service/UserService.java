package com.offcn.user.service;

import com.offcn.user.po.TMember;
import com.offcn.user.po.TMemberAddress;

import java.util.List;

public interface UserService {

    /**
     * 用户注册
     * @param member
     */
    public void registerUser(TMember member);

    public TMember login(String username,String password);

    /**
     * 根据用id查询
     * @param id
     * @return
     */
    public TMember findTMemberById(Integer id);

    /**
     * 获取用户收货地址
     * @param memberId
     * @return
     */
    List<TMemberAddress> addressList(Integer memberId);

}
