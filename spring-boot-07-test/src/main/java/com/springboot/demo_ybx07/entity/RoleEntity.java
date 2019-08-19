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
 * 角色entity.
 *
 * @author 杨冰鑫
 * @since 2019/8/15 23:33
 */
@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue
    private int id;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "rid") }, inverseJoinColumns = {
        @JoinColumn(name = "pid") })
    private List<PermissionEntity> permissions;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "rid") }, inverseJoinColumns = {
        @JoinColumn(name = "uid") })
    private List<UserEntity> users;

}
