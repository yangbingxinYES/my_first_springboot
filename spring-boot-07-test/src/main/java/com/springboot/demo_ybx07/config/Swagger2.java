package com.springboot.demo_ybx07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger自定义配置.
 *
 * @author 杨冰鑫
 * @since 2019/8/11 13:35
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("springboot 项目接口文档")
            .description("Magical Sam 项目的接口文档，符合RESTful API。")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.springboot.demo_ybx07.controller")) //以扫描包的方式,扫描包下
            .paths(PathSelectors.any())
            .build();
    }

}
