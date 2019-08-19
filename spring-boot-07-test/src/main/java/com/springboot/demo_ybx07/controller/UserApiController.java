package com.springboot.demo_ybx07.controller;

import com.springboot.demo_ybx07.entity.User;
import com.springboot.demo_ybx07.entity.UserEntity;
import com.springboot.demo_ybx07.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import exception.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *测试的 UserApiController .
 *
 * @author 杨冰鑫
 * @since 2019/8/11 13:44
 */
@RestController
@Api(tags = "用户模块")
@RequestMapping("/user/")
public class UserApiController {
    @Resource
    private UserService userService;
    /**
     * 测试接口，获取单个用户
     */
    @ApiOperation(value = "获取单个用户",notes = "传入id")
    @GetMapping("getUser/{id}")
    public User user(@ApiParam(value = "用户id",required = true)@PathVariable int id){
        User user = new User();
        if (id == 1){
            user.setId(1);
            user.setName("ybx");
            user.setAge(21);
            return user;
        }else {
            user.setMsg("该用户不存在！");
        }

        return user;
    }
    @ApiOperation(value = "获取单个用户",notes = "传入id")
    @GetMapping("exception/{eid}")
    public String exception(@ApiParam(value = "eid",required = true)@PathVariable("eid") int id) throws Exception{
       String s;
        if (id ==1){
            s = "ok";
        }else {
            throw new MyException("-1","异常：该eid不存在！");
        }
    return s;
    }
    @ApiOperation(value = "获取列表",notes = "name")
    @GetMapping("userBy")
    public UserEntity name(String name) throws Exception{
        UserEntity userByName = userService.findUserByName(name);
        return userByName;
    }
}
