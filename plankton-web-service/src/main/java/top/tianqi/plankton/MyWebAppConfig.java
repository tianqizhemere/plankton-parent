package top.tianqi.plankton;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置tomcat虚拟路径
 * @author Wukh
 * @create 2021-01-19
 */
@Configuration
public class MyWebAppConfig implements WebMvcConfigurer {

    @Value("${attachUploadBase}")
    private String attachUploadBase;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/file/**").addResourceLocations("file:"+ attachUploadBase +"/upload/file/");
    }
}
