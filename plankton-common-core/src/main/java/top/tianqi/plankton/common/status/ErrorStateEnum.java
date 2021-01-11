package top.tianqi.plankton.common.status;

/**
 * 响应状态码枚举
 * 本项目异常使用四位数
 * 第三方接口异常使用五位数
 * @author Wukh
 * @create 2021-01-04
 */
public enum ErrorStateEnum {

    REQUEST_SUCCESS(200, "OK"),
    /**
     * 未登录
     */
    REQUEST_UNAUTHC_EXCEPTION(411, "未登录"),
    /**
     * 未授权
     */
    REQUEST_UNPERMS_EXCEPTION(412, "未授权"),
    // 用户名不存在
    USERNAME_NOT_EXIST(1004, "用户名不存在"),
    // 密码不正确
    PASSWORD_ERROR(1005, "密码不正确"),
    // 没有相关权限
    NOT_AUTH(1006, "没有相关权限"),
    // token无效
    TOKEN_INVALID(1007, "token failure!"),
    // 缺少相应参数
    MISSING_PARAMETER(1008, "参数绑定失败:缺少参数"),
    // 接口请求限制
    REQUEST_LIMIT(1009, "请求频繁,请稍后重试"),
    // refreshToken无效
    REFRESH_TOKEN_INVALID(1010, "认证过期,请重新登录");
    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 原因短语
     */
    private final String msg;

    ErrorStateEnum(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}