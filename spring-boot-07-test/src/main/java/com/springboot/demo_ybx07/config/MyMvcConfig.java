package com.springboot.demo_ybx07.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.springboot.demo_ybx07.component.MyHandlerInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 *拦截器配置 .
 *
 * 1.使用FastJSON
 * 2.配置时间格式化
 * 3.解决中文乱码
 * 4.添加视图controller
 * 5.添加自定义拦截器
 * @author 杨冰鑫
 * @since 2019/8/12 21:29
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer{
    /**
     * 自定义json转换器(适配器)
     * @param converters 转换器
     */
    @Override
    public void configureMessageConverters(
        List<HttpMessageConverter<?>> converters) {
        //1.构建了一个HttpMessageConverter  FastJson   消息转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //2.定义一个配置，设置编码方式，和格式化的形式
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //3.设置成了PrettyFormat格式
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //4、日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //5、处理中文乱码问题
        List<MediaType> fastMediaType = new ArrayList<>();
        fastMediaType.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaType);
        //6、将fastJsonConfig加到消息转换器中
        converter.setFastJsonConfig(fastJsonConfig);
        for (int i = 0; i<converters.size();i++){
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter){
                converters.set(i,converter);
            }
        }
//        converters.add(converter);
    }

    /**
     * 跳转页面，视图控制
     * @param registry
     */
    @Override
    public void addViewControllers(
        ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("index");
    }

    /**
     * 注册拦截器
     *
     * addPathPatterns 拦截路径 /** 表示拦截所有
     * excludePathPatterns 不拦截路径
     * MyHandlerInterceptor 自己写的拦截器类
     * @param registry 注册
     */
    @Override
    public void addInterceptors(
        InterceptorRegistry registry) {
            registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/login.html","/user/login","/csrf ");

    }

    /**
     * 静态资源访问
     * @param registry 注册
     */
    @Override
    public void addResourceHandlers(
        ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
