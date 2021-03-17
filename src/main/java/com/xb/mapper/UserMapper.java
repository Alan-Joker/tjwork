package com.xb.mapper;

import com.xb.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Alan_
 * @create 2021/3/17 15:44
 */
@Mapper
public interface UserMapper {
    User selectByName(String username);

    Boolean inserUser(@Param("user") User user);

    List<User> selectList();

    void updateById(User user);

    int deleteById(Integer id);

    User selectById(Integer id);
}
