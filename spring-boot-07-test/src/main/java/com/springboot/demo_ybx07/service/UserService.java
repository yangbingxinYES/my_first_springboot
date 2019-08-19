package com.springboot.demo_ybx07.service;

import com.springboot.demo_ybx07.entity.UserEntity;

/**
 * 用户service.
 *
 * @author 杨冰鑫
 * @since 2019/8/16 9:53
 */
public interface UserService {
    /**
     *根据用户名称查询数据
     * @param name 用户名称
     * @return UserEntity
     */
    public UserEntity findUserByName(String name);

}
