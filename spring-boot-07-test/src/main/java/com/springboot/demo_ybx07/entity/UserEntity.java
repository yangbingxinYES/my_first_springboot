package com.springboot.demo_ybx07.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 *用户entity .
 *
 * @author 杨冰鑫
 * @since 2019/8/15 23:33
 */
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;
    private String userName;//账号
    private String name;//名称
    private String passWord;//密码
    private String salt;//密码加密的盐，主要加强密码的安全性
    private String state;//用户状态，0：创建用户，1：正常，2：用户锁定

    /**
     * 关联配置  用户--角色，多对多
     *
     *
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
        @JoinColumn(name = "rid") })
    private List<RoleEntity> roles;

    public String getCredentialsSalt() {
        System.err.println("因子"+userName + salt);
        return userName + salt;
    }

}
