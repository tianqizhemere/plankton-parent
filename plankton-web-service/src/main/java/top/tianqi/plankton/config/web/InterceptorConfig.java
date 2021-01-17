package top.tianqi.plankton.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.tianqi.plankton.config.shiro.filter.JwtFilter;

/**
 * 拦截器配置
 * @author Wukh
 * @create 2021-01-17
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 设置拦截路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
    /**
     * 将拦截器注入context
     * return JwtFilter
     */
    @Bean
    public JwtFilter authenticationInterceptor() {
        return new JwtFilter();
    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }
}
