package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户的操作日志
 * @author tianQi
 * @create 2021-01-07
 */
@TableName(value = "sys_log")
public class OperationLog extends BaseEntity {

    private static final long serialVersionUID = 3513566928111474788L;

    /** 功能模块 */
    private String model;

    /** 操作类型*/
    private String type;

    /** 操作描述 */
    private String desc;

    /** 请求参数 */
    private String requestParam;

    /** 响应参数 */
    private String responseParam;

    /** 用户id */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 操作方法 */
    private String method;

    /** 请求URI */
    private String uri;

    /** 请求IP */
    private String ip;
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
