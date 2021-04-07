package top.tianqi.plankton.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.tianqi.plankton.web.filter.XssFilter;

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
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/,/owa/auth/logon.aspx," +
                "/api/jsonws/invoke,/vendor/phpunit/phpunit/src/Util/PHP/eval-stdin.php," +
                "/mifs/.;/services/LogService,/_ignition/execute-solution,/wp-content/plugins/wp-file-manager/readme.txt,/actuator/health");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
