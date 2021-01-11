package top.tianqi.plankton.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * 常量类
 * @author Wukh
 * @create 2021-01-10
 */
public class Constant {

    /**
     * 验证码过期时间 此处为五分钟
     */
    public static Integer CODE_EXPIRE_TIME = 5;

    /**
     * jwtToken过期时间 20分钟
     */
    public static Long TOKEN_EXPIRE_TIME = TimeUnit.MINUTES.toMillis(20);

    /**
     * jwtToken刷新时间　7天
     */
    public static Long TOKEN_REFRESH_TIME = TimeUnit.DAYS.toMillis(7);

    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "X-Token";

    /**
     * 表单重复提交间隔时间 单位 S
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    public static int FORM_REPEAT_TIME = 10;
}