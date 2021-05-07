package top.tianqi.plankton.util;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Http Rest 请求工具类
 * @author Wukh
 * @create 2021-04-22
 */
@Component
public final class RestClientUtil {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate client) {
        restTemplate = client;
    }

    /**
     * GET请求
     * @param url       请求地址
     * @param headerMap 头部信息
     * @param resp      响应结果类型
     * @return Object 响应结果
     */
    public static Object get(String url, Map<String, String> headerMap, Class<?> resp) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, resp);
        return result.getBody();
    }

    /**
     * GET请求
     * @param url       请求地址
     * @param headerMap 头部信息
     * @return Object 响应结果
     */
    public static Object get(String url, Map<String, String> headerMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
            httpHeaders.add(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<?> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);
        return result.getBody();
    }

    /**
     * GET请求
     * @param <T> 响应结果类型
     * @param url 请求路径
     * @param clazz 字节码对象
     * @return T
     */
    public static <T> T get(String url, Class<T> clazz) {
        return restTemplate.getForObject(url , clazz);
    }

    /**
     * POST请求(JSON数据格式)
     * @param requestUrl 请求路径
     * @param requestJsonStrParam json格式请求参数
     * @return 返回值
     */
    public static String postJson(String requestUrl, String requestJsonStrParam) {
        // 设置 Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        // 设置参数
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJsonStrParam, httpHeaders);
        // 执行请求
        ResponseEntity<String> resp = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, String.class);
        // 返回请求返回值
        return resp.getBody();
    }

    /**
     * POST请求(Form表单数据格式)
     * @param requestUrl  请求路径
     * @param requestFormPara Form表单请求格式
     * @return 返回值
     */
    public static String postForm(String requestUrl, String requestFormPara) {
        // 设置 Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 设置参数
        HttpEntity<String> requestEntity = new HttpEntity<>(requestFormPara, httpHeaders);
        // 执行请求
        ResponseEntity<String> resp = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, String.class);
        // 返回请求返回值
        return resp.getBody();
    }

    public static void main(String[] args) {
        String jsonStr = "{\"name\":\"BeJson\",\"url\"}";
        RestClientUtil.postJson("http://www.baidu.com", jsonStr);
    }
}
