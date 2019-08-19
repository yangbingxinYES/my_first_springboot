package com.springboot.demo_ybx07.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 *测试的user .
 *
 * @author 杨冰鑫
 * @since 2019/8/11 13:43
 */
@Data
@ApiModel(value = "用户返回类")
public class User {

    private int id;
    private String name;
    private int age;
    private String msg;

}