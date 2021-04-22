package top.tianqi.plankton.enumertaion;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import top.tianqi.plankton.interceptor.HttpLoggingInterceptor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate单例枚举类
 * @author Wukh
 * @create 2021-04-22
 */
public enum RestEnum {

    SINGLE_INSTANCE;

    private final RestTemplate restTemplate;

    RestEnum() {
        // 设置超时
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();

        // 设置日志拦截
        ClientHttpRequestInterceptor ri = new HttpLoggingInterceptor();
        List<ClientHttpRequestInterceptor> ris = new ArrayList<>();
        ris.add(ri);
        restTemplate.setInterceptors(ris);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
