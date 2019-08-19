package com.springboot.demo_ybx07.dao.jpa;

import com.springboot.demo_ybx07.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * .
 *
 * @author 杨冰鑫
 * @since 2019/8/11 14:45
 */
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

}
