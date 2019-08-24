package com.springboot.demo_ybx07.config;

import com.springboot.demo_ybx07.component.EnceladusShiroRealm;
import com.springboot.demo_ybx07.util.PasswordHelper;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类.
 *
 * @author 杨冰鑫
 * @since 2019/8/16 10:13
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //修改默认跳转
        //没有登录的用户请求需要登录的页面时自动跳转到登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthc");
        //登录成功默认跳转页面，不配置则跳转至"/"
        shiroFilterFactoryBean.setSuccessUrl("/home/index");

        //拦截器
//        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断,
        // anno不进行任何过滤，authc需要认证，
        //user:如果使用rememberMe的功能可以直接访问
        //perms:该资源必须得到资源权限才可以访问
        //role:该资源必须得到角色权限才可以访问
        //放行swagger
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/springboot/**","anon");
        //放行druid
        filterChainDefinitionMap.put("/druid/**","anon");
        //放行登录接口
        filterChainDefinitionMap.put("/doLogin","anon");
        //放行登录退出接口
        filterChainDefinitionMap.put("/logout","anon");
        //放行注册接口
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/authc/index", "authc");
        filterChainDefinitionMap.put("/authc/admin", "roles[admin]");
        filterChainDefinitionMap.put("/authc/renewable", "perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/removable", "perms[Delete]");
//        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS); // 散列次数
        return hashedCredentialsMatcher;
    }

    /**
     * 创建realm
     * @return EnceladusShiroRealm
     */
    @Bean
    public EnceladusShiroRealm shiroRealm() {
        EnceladusShiroRealm shiroRealm = new EnceladusShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
        return shiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }

}
