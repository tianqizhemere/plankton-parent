package top.tianqi.plankton.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import top.tianqi.plankton.filter.XssFilter;
import top.tianqi.plankton.web.system.service.MailService;

import java.util.HashMap;
import java.util.Map;

/**
 * 本项目配置类
 * 加载自定义拦截器和过滤器和RPC远程调用对象
 * @author Wukh
 * @create 2021-03-24
 */
@Configuration(proxyBeanMethods = false)
public class PlanktonConfig {

    private static final Logger logger = LoggerFactory.getLogger(PlanktonConfig.class);

    /** 邮件RPC调用地址 */
    @Value("${remoteMailUrl}")
    private String remoteMailUrl;

    /**
     * 注册XssFilter
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>(3);
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    /**
     * 测试远程调用
     * 邮件服务远程调用
     */
    @Bean
    public HttpInvokerProxyFactoryBean remoteMail(){
        logger.info(">>>>>>>>>>> 邮件RPC远程调用init >>>>>>>>>>>");
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceUrl(remoteMailUrl);
        httpInvokerProxyFactoryBean.setServiceInterface(MailService.class);
        return httpInvokerProxyFactoryBean;
    }
}
