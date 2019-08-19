package com.springboot.demo_ybx07.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.demo_ybx07.dao.jpa.DepartmentRepository;
import com.springboot.demo_ybx07.dao.mapper.DepartmentMapper;
import com.springboot.demo_ybx07.entity.Department;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import exception.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *测试jpa .
 *
 * @author 杨冰鑫
 * @since 2019/8/11 14:48
 */
@RestController
@Api(value = "测试dao模块")
@RequestMapping("/dao/")
public class DepartmentApiController {
    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private DepartmentMapper departmentMapper;

    @ApiOperation(value = "获取单个数据",notes = "传入id")
    @GetMapping("jpa/{id}")
    public Department jpa(@ApiParam(value = "id",required = true)@PathVariable("id") int id) throws Exception{

        Department department = new Department();
        if (id ==1){

            department.setDepartmentName("这是对jpa的测试！");
            department = departmentRepository.save(department);
            Optional<Department> entity = departmentRepository.findById(department.getId());
            department.setId(entity.get().getId());
            department.setDepartmentName(entity.get().getDepartmentName());
        }else {
            throw new MyException("-1", "异常：该id不存在！");
        }
        return department;
    }
    @ApiOperation(value = "获取全部数据",notes = "")
    @GetMapping("mybatis/{pageNum}")
    public PageInfo<com.springboot.demo_ybx07.pojo.Department> mybatis(@ApiParam(value = "pageNum",required = true)@PathVariable("pageNum") int pageNum) throws Exception{
        PageHelper.startPage(pageNum,5);
        List<com.springboot.demo_ybx07.pojo.Department> departments = departmentMapper.selectAll();
        PageInfo<com.springboot.demo_ybx07.pojo.Department> pageInfo = new PageInfo<com.springboot.demo_ybx07.pojo.Department>(departments);

        if (pageInfo == null){
            throw new MyException("-1", "异常：数据不存在！");
        }
        return pageInfo;
    }
}
