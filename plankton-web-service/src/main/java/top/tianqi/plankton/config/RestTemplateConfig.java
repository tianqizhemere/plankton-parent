package top.tianqi.plankton.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import top.tianqi.plankton.interceptor.HttpLoggingInterceptor;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate配置类
 * @author Wukh
 * @create 2021-04-22
 */
@Configuration
@ConditionalOnClass(ProxyConfig.class)
public class RestTemplateConfig {

    /** 读取超时时间 */
    @Value("${rest.ReadTimeout}")
    private int readTimeout;

    /** 连接超时时间 */
    @Value("${rest.ConnectTimeout}")
    private int connectionTimeout;

    @Autowired
    private ProxyConfig proxyConfig;

    /**
     * 代理配置
     * @return
     */
    @Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(readTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);
        if(proxyConfig.getEnabled()){
            SocketAddress address = new InetSocketAddress(proxyConfig.getHost(), proxyConfig.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            httpRequestFactory.setProxy(proxy);
        }
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        // 设置超时
       RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(readTimeout))
                .setReadTimeout(Duration.ofMillis(connectionTimeout))
                .requestFactory(this::httpClientFactory)
                .build();
        // 设置日志拦截
        ClientHttpRequestInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(httpLoggingInterceptor);
        restTemplate.setInterceptors(interceptors);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return restTemplate;
    }
}
