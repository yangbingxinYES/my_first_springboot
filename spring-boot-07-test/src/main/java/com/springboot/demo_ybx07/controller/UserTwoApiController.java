package com.springboot.demo_ybx07.controller;

import com.springboot.demo_ybx07.entity.UserEntity;
import com.springboot.demo_ybx07.service.UserService;
import com.springboot.demo_ybx07.util.PasswordHelper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 测试shiro.
 *
 * @author 杨冰鑫
 * @since 2019/8/16 13:49
 */
@RestController
@Api(tags = "测试shiro1")
public class UserTwoApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @ApiOperation(value = "测试login",notes = "")
    @GetMapping("login")
    public Object login() {
        return "请先登陆！";
    }
    @ApiOperation(value = "测试unautc",notes = "")
    @GetMapping("unauthc")
    public Object unauthc() {
        return "没有该权限哦！";
    }

    /**
     * 使用shiro编写认证操作
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "测试doLogin",notes = "")
    @GetMapping("doLogin")
    public Object doLogin(@RequestParam String username, @RequestParam String password) {
       //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
       //获取Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            //执行登录方法
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            //密码错误
            return "password error!";
        } catch (UnknownAccountException uae) {
            //用户名不存在
            return "username error!";
        }

        UserEntity user = userService.findUserByName(username);
        subject.getSession().setAttribute("user", user);
        return "登陆成功";
    }
    @ApiOperation(value = "测试注册",notes = "username,password")
    @GetMapping("register")
    public Object register(@RequestParam String username, @RequestParam String password) {
        UserEntity user = new UserEntity();
        user.setUserName(username);
        user.setPassWord(password);
        passwordHelper.encryptPassword(user);
//        userService.saveUser(user);
        return "注册成功";
    }
    @ApiOperation(value = "退出登录",notes = "username,password")
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "推出成功";
    }
}
