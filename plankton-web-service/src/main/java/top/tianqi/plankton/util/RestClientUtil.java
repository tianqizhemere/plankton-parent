package top.tianqi.plankton.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import top.tianqi.plankton.enumertaion.RestEnum;

/**
 * Http请求工具类
 * @author Wukh
 * @create 2021-04-22
 */
public final class RestClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestClientUtil.class);

    /**
     * POST请求(JSON数据格式)
     * @param reqUrl 请求路径
     * @param reqJsonStrParam json格式请求参数
     * @return 返回值
     */
    public static String postJson(String reqUrl, String reqJsonStrParam) {
        // 设置 Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        // 设置参数
        HttpEntity<String> requestEntity = new HttpEntity<>(reqJsonStrParam, httpHeaders);
        // 执行请求
        ResponseEntity<String> resp = RestEnum.SINGLE_INSTANCE.getRestTemplate()
                .exchange(reqUrl, HttpMethod.POST, requestEntity, String.class);
        // 返回请求返回值
        return resp.getBody();
    }

    /**
     * POST请求(Form表单数据格式)
     * @param reqUrl  请求路径
     * @param reqFormPara Form表单请求格式
     * @return 返回值
     */
    public static String postForm(String reqUrl, String reqFormPara) {
        // 设置 Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 设置参数
        HttpEntity<String> requestEntity = new HttpEntity<>(reqFormPara, httpHeaders);
        // 执行请求
        ResponseEntity<String> resp = RestEnum.SINGLE_INSTANCE.getRestTemplate()
                .exchange(reqUrl, HttpMethod.POST, requestEntity, String.class);
        // 返回请求返回值
        return resp.getBody();
    }

    public static void main(String[] args) {
        String jsonStr = "{\"name\":\"BeJson\",\"url\"}";
        System.out.println(RestClientUtil.postJson("http://www.baidu.com", jsonStr));
    }
}
