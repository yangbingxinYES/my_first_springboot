package com.springboot.demo_ybx07.dao.mapper;

import com.springboot.demo_ybx07.entity.UserEntity;

import org.apache.ibatis.annotations.Param;

/**
 *用户mapper .
 *
 * @author 杨冰鑫
 * @since 2019/8/16 13:46
 */
public interface UserMapper {

    public UserEntity findByName(@Param("userName")String name);
}
