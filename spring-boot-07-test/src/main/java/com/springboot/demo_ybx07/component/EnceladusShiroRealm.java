package com.springboot.demo_ybx07.component;

import com.springboot.demo_ybx07.entity.PermissionEntity;
import com.springboot.demo_ybx07.entity.RoleEntity;
import com.springboot.demo_ybx07.entity.UserEntity;
import com.springboot.demo_ybx07.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的Realm--AuthorizingRealm(用于实现doGetAuthenticationInfo和doGetAuthorizationInfo).
 *
 * @author 杨冰鑫
 * @since 2019/8/16 9:41
 */
@Slf4j
public class EnceladusShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    /**
     * 负责提供一个权限信息
     * @param principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
        PrincipalCollection principals) {
        log.info("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //方式1： 通过认证之中的SimpleAuthenticationInfo（Object arg）的第一个参数，传递进来的，入参是泛型，
            // 本次是userEntity,所以不可以通过string 接受，采用以下；
        // 方式2：如果传入的参数为string类型， 像SimpleAuthenticationInfo（String userNmae）,就这么拿数据
            // String userName = (String) principals.getPrimaryPrincipal();
            //但满足本次授权需要user的角色、权限，还需
            // UserEntity user = userService.findUserByName(userName); 这样的话就不需要去拿入参了，多此一举
        //取出对象,相当于从session中获得用户
         UserEntity user = (UserEntity)principals.fromRealm(this.getClass().getName()).iterator().next();

        for (RoleEntity role : user.getRoles()) {
            authorizationInfo.addRole(role.getRole());
            for (PermissionEntity permission : role.getPermissions()) {
                //添加资源的授权字符串
                authorizationInfo.addStringPermission(permission.getPName());
            }
        }

        return authorizationInfo;
    }

    /**
     * 负责登陆认证
     * @param token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
        AuthenticationToken token) throws AuthenticationException {
      //  String username = (String) token.getPrincipal();
//        UserEntity user = userService.findUserByName(username);

//        1、判断用户名
        UsernamePasswordToken tokens = (UsernamePasswordToken)token;
        UserEntity user = userService.findUserByName(tokens.getUsername());
        if (!tokens.getUsername().equals(user.getUserName())){
            //用户名不存在
            return null;
            //shiro底层会抛出UnknownAccountException
        }
        //2、判断密码,第一个参数是返回方法的参数--身份信息，第二个参数是密文密码--凭据，第三个是盐--username+salt，第四个类的名字
        return new SimpleAuthenticationInfo(user,user.getPassWord(), ByteSource.Util.bytes(user.getCredentialsSalt()),this.getClass().getName());

        //CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。本项目使用自定义加密--PasswordHelper

        //编写shiro 判断逻辑，判断用户名和密码
       /* if (user == null) {
            return null;
        }*/
//        SimpleAuthenticationInfo
//            authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassWord(),
//                  ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
      //  return authenticationInfo;
    }
}
