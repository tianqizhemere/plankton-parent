package top.tianqi.plankton.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.tianqi.plankton.filter.XssFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 本项目配置类
 * 加载自定义拦截器和过滤器
 * @author Wukh
 * @create 2021-03-24
 */
@Configuration(proxyBeanMethods = false)
public class PlanktonConfig {

    /**
     * XssFilter Bean
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
}
