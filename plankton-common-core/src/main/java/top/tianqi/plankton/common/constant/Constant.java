package top.tianqi.plankton.common.constant;

import java.util.concurrent.TimeUnit;

/**
 * 常量类
 * @author Wukh
 * @create 2021-01-10
 */
public class Constant {

    private Constant(){}

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

    /**
     * 是否用户已被冻结 1(解冻)正常
     */
    public static final Integer USER_UNFREEZE = 1;

    /**
     * 是否用户已被冻结  0冻结
     */
    public static final Integer USER_FREEZE = 0;


    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-ieml(移动设备号):
     */
    public static final String ACCOUNT = "ieml";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

}
