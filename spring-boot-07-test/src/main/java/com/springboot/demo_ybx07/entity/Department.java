package com.springboot.demo_ybx07.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 测试jpa实体类.
 *
 * @author 杨冰鑫
 * @since 2019/8/11 14:41
 */
@Data
@Entity
@Table(name = "department")//指定表名
public class Department {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String departmentName;
}
