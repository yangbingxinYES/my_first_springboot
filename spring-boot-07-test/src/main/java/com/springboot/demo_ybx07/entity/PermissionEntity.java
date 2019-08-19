package com.springboot.demo_ybx07.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 *权限entity .
 *
 * @author 杨冰鑫
 * @since 2019/8/15 23:33
 */
@Entity
@Data
@Table(name = "permission")
public class PermissionEntity {
    @Id
    @GeneratedValue
    private int id;
    private String pName;
    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "pid") }, inverseJoinColumns = {
        @JoinColumn(name = "rid") })
    private List<RoleEntity> roles;
}
