package com.springboot.demo_ybx07.controller;

import com.springboot.demo_ybx07.entity.UserEntity;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试shiro3.
 *
 * @author 杨冰鑫
 * @since 2019/8/16 14:05
 */
@RestController
@Api(tags = "测试shiro功能3")
@RequestMapping("authc")
@Slf4j
public class UserThreeApiController {
    @GetMapping("index")
    @ApiOperation(value = "测试index",notes = "")
    public Object index() {
        log.error("进入index");
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getSession().getAttribute("user");
        return user.toString();
    }

    @GetMapping("admin")
    @ApiOperation(value = "测试admin",notes = "")
    public Object admin() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        int a = Integer.parseInt(df.format(date));
        String msg = "";
        if (a >=0 && a<= 6){
            msg = "【凌晨】"+a;
        }
        if (a>6&&a<=12) {
            msg = "【上午】"+a;
        }
        if (a>12&&a<=13) {
            msg = "【中午】"+a;
        }
        if (a>18&&a<=24) {
            msg = "【晚上】"+a;
        }
        return "欢迎您，超级管理员,现在是"+msg+"点";
    }

    // delete
    @ApiOperation(value = "测试removable",notes = "")
    @GetMapping("removable")
    public Object removable() {
        return "removable";
    }

    // insert & update
    @ApiOperation(value = "测试renewable",notes = "")
    @GetMapping("renewable")
    public Object renewable() {
        return "renewable";
    }
}
