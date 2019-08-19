package com.springboot.demo_ybx07.controller;

import com.springboot.demo_ybx07.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 *RedisController .
 *
 * @author 杨冰鑫
 * @since 2019/8/15 15:41
 */
@RestController
@RequestMapping("/redis")
@Api(value = "Redis模块")
@Slf4j
public class RedisController{
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "getRedis",notes = "2019")
    @GetMapping("getRedis/{str}")
    public ModelMap getRedis(@ApiParam(value = "string",required = true)@PathVariable String str){
        ModelMap modelMap = new ModelMap();
        String[] list = str.split("2");
        redisUtil.set(list[0],list[1],0);
        Long resExpire = redisUtil.expire(list[0], 60, 0);//设置key过期时间
        log.info("resExpire="+resExpire);
        String res = redisUtil.get(list[0], 0);

        modelMap.addAttribute(list[0],res);
        return modelMap;
    }
}
