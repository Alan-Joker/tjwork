package com.xb.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xb.entity.Result;
import com.xb.entity.User;
import com.xb.service.UserSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Alan_
 * @create 2021/3/17 15:37
 */
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserManger {

    @Autowired
    private UserSerive userSerive;

    @PostMapping("/regiser")
    public Result register(@RequestParam User user){
        User info = userSerive.getByUserName(user.getUsername());
        if(info != null){
            return Result.error().message("用户已存在");
        }
        Boolean flag = userSerive.insertUser(user);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 分页查询用户信息
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/serchList")
    public Result login(@RequestParam Integer page,@RequestParam Integer rows){
        PageHelper.startPage(page,rows);
        List<User> list = userSerive.selctList();
        for (User user : list) {
            user.setPassword("不告你");
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        //总记录数
        long total = pageInfo.getTotal();
        //当前页数据
        List<User> userList = pageInfo.getList();
        //封装数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("userList",userList);
        return Result.ok().data(map);
    }
    /**
     * 修改用户信息
     */
    @PutMapping("update")
    public Result updateUserName(@RequestParam User user){
        User userInfo = userSerive.getByUserName(user.getUsername());
        if(userInfo != null){
            return Result.error().message("用户名已存在,请重试");
        }
        userSerive.updateById(user);
        return Result.ok();
    }
    /**
     * 根据用户id回显用户信息
     */
    @GetMapping("/serchById/{id}")
    public Result serchByid(@PathVariable("id") Integer id){
        User user = userSerive.getById(id);
        user.setPassword("不告你");
        return Result.ok().data("user",user);
    }
    /**
     * 根据用户id删除用户信息
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        boolean flag = userSerive.deleteById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}
