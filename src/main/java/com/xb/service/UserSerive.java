package com.xb.service;

import com.xb.entity.User;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/3/17 15:43
 */
public interface UserSerive {
    //根据用户昵称查询用户是否存在
    User getByUserName(String  username);

    //插入用户信息
    Boolean insertUser(User user);

    List<User> selctList();

    void updateById(User user);

    boolean deleteById(Integer id);

    User getById(Integer id);
}
