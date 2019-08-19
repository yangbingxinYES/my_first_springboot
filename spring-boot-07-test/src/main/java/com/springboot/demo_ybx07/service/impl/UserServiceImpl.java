package com.springboot.demo_ybx07.service.impl;

import com.springboot.demo_ybx07.dao.mapper.UserMapper;
import com.springboot.demo_ybx07.entity.UserEntity;
import com.springboot.demo_ybx07.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户相关逻辑.
 *
 * @author 杨冰鑫
 * @since 2019/8/16 9:53
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public UserEntity findUserByName(String name) {
        return userMapper.findByName(name);
    }
}
