package com.springboot.demo_ybx07.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *拦截器 .
 *
 * @author 杨冰鑫
 * @since 2019/8/12 21:12
 */
public class MyHandlerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    /**
     * 请求方法执行之前
     * 返回true则通过
     *
     * @param request 请求
     * @param response 响应
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuffer requestURL = request.getRequestURL();
        logger.info("preHandle"+requestURL);
        return true;
    }

    /**
     * 返回modelAndView之前
     *
     * @param request 请求
     * @param response 响应
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
        logger.info("postHandle返回modelAndView之前");
    }

    /**
     * 执行完请求方法完全返回之后
     * @param request 请求
     * @param response 响应
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("afterCompletion执行完请求方法完全返回之后");
    }
}
