package com.xb.service;

import com.xb.entity.User;
import com.xb.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/3/17 15:51
 */
@Service
@Transactional
public class UserServiceImpl implements UserSerive {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    public Boolean insertUser(User user) {
        return userMapper.inserUser(user);
    }

    @Override
    public List<User> selctList() {

        return userMapper.selectList();
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    @Override
    public boolean deleteById(Integer id) {
        int row = userMapper.deleteById(id);
        System.out.println(row);
        return row > 0;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }
}
