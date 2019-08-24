package com.springboot.demo_ybx07.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置.
 *
 * @author 杨冰鑫
 * @since 2019/8/24 23:45
 */
/*@Configuration
public class FileUploadConfig {
    *//**
     * 文件上传配置
     *
     * @return MultipartConfigElement
     *//*
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize("50Mb");
        factory.setMaxFileSize();
        // 设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
*/