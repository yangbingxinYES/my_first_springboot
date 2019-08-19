package com.springboot.demo_ybx07.dao.mapper;


import com.springboot.demo_ybx07.pojo.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 列表
     * @return Department
     */
    List<Department> selectAll();
}