package top.tianqi.plankton.config;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.tianqi.plankton.common.shiro.MyShiroRealm;
import top.tianqi.plankton.common.shiro.ShiroLoginFilter;
import top.tianqi.plankton.common.shiro.ShiroPermsFilter;
import top.tianqi.plankton.common.shiro.ShiroSessionManager;
import top.tianqi.plankton.common.shiro.filter.JwtFilter;

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
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * shiro 拦截器拦截到所有请求后，调用此方法
     * Shiro内置过滤器，可以实现拦截器相关的拦截器
     * 常用的过滤器：
     *   anon：无需认证（登录）可以访问
     *   authc：必须认证才可以访问
     *   user：如果使用rememberMe的功能可以直接访问
     *   perms：该资源必须得到资源权限才可以访问
     *   role：该资源必须得到角色权限才可以访问
     * @param manager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();

        factory.setSecurityManager(manager);

        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();

//        filtersMap.put("ShiroLoginFilter", shiroLoginFilter());
        filtersMap.put("ShiroPermsFilter", shiroPermsFilter());
        filtersMap.put("jwt", jwtFilter());
        factory.setFilters(filtersMap);

        // 设置过滤链
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        filterChainMap.put("/login", "anon");
        filterChainMap.put("/logout", "anon");
        filterChainMap.put("/**", "jwt");

        factory.setFilterChainDefinitionMap(filterChainMap);

        // 没有认证用户,shiro 会跳转到登录页面,前后端分离项目后端不控制跳转,跳转到未授权界面,返回 json
        factory.setLoginUrl("/login");
        factory.setUnauthorizedUrl("/401");

        return factory;
    }


    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    /**
     * 安全管理器
     *
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(MyShiroRealm realm) {
        DefaultWebSecurityManager serurityManeger = new DefaultWebSecurityManager(realm);
        // 自定义缓存实现
        serurityManeger.setCacheManager(cacheManager());
        // realm
        serurityManeger.setRealm(realm);
        // 自定义 session 管理
        serurityManeger.setSessionManager(sessionManager());
        return serurityManeger;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * Session Manager 使用的是 shiro-redis 开源插件
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setGlobalSessionTimeout(30 * 24 * 60 * 60 * 1000);
        return sessionManager;
    }

    /**
     * 配置 shiro redisManager 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        //redisManager.setPassword(password);
        // 配置缓存过期时间
        redisManager.setExpire(30 * 24 * 60 * 60);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * cacheManager 缓存，使用shiro-redis 开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * shiro sessionDao 层的实现
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 自定义 Realm
     *
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public MyShiroRealm customRealm() {
        return new MyShiroRealm();
    }

    /**
     * 自定义认证拦截器
     *
     * @return
     */
    @Bean(name = "shiroLoginFilter")
    public ShiroLoginFilter shiroLoginFilter() {
        ShiroLoginFilter advice = new ShiroLoginFilter();
        return advice;
    }

    /**
     * 自定义授权拦截器
     *
     * @return
     */
    @Bean(name = "shiroPermsFilter")
    public ShiroPermsFilter shiroPermsFilter() {
        ShiroPermsFilter advice = new ShiroPermsFilter();
        return advice;
    }

    /**
     * 自定义凭证校验
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        return matcher;
    }

    /**
     *  shiro 注解拦截器
     *
     * @param securityManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
