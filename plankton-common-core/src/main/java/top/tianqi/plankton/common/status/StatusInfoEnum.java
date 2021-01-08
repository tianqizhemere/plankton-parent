package top.tianqi.plankton.common.status;

/**
 * 响应状态码枚举
 * @author Wukh
 * @create 2021-01-04
 */
public enum StatusInfoEnum {

    REQUEST_SUCCESS(200, "OK"),
    /**
     * 用户名 或密码错误
     */
    REQUEST_USERNAMEORPASSWORD_EXCEPTION(410, "用户名或密码错误"),
    /**
     * 未登录
     */
    REQUEST_UNAUTHC_EXCEPTION(411, "未登录"),
    /**
     * 未授权
     */
    REQUEST_UNPERMS_EXCEPTION(412, "未授权");
    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 原因短语
     */
    private final String message;

    StatusInfoEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
