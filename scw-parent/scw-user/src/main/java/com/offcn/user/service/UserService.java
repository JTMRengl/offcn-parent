package com.offcn.user.service;

import com.offcn.user.po.TMember;

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
}
