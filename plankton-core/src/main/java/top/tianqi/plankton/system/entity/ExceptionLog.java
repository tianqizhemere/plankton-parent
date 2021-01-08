package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 异常操作日志
 * @author Wukh
 * @create 2021-01-08
 */
@TableName(value = "exception_log")
public class ExceptionLog extends BaseEntity {

    private static final long serialVersionUID = 2657837317103914160L;

    /** 异常信息 */
    private String message;

    /** 异常名称 */
    private String name;

    /** 请求参数 */
    private String requestParam;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
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
