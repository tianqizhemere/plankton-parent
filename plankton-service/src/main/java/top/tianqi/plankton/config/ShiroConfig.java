package top.tianqi.plankton.config;


import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.tianqi.plankton.shiro.MyShiroRealm;
import top.tianqi.plankton.shiro.cache.CustomCacheManager;
import top.tianqi.plankton.filter.JwtFilter;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 * @author Wukh
 * @create 2021-01-04
 */
@Configuration
public class ShiroConfig {

    /**
     * shiro 拦截器拦截到所有请求后，调用此方法
     * @param manager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();

        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("jwt", jwtFilter());
        factory.setFilters(filtersMap);
        factory.setSecurityManager(manager);

        // 设置过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(16);
        filterChainDefinitionMap.put("/**", "jwt");
        factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factory;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    /**
     * 安全管理器
     * 配置使用自定义Realm，关闭Shiro自带的session
     * @param myShiroRealm 自定义realm
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager serurityManeger = new DefaultWebSecurityManager();
        // 使用自定义realm
        serurityManeger.setRealm(myShiroRealm);
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        serurityManeger.setSubjectDAO(subjectDAO);
        // 设置自定义Cache缓存
        serurityManeger.setCacheManager(new CustomCacheManager());
        return serurityManeger;
    }

    /**
     * 添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * shiro 注解拦截器
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
