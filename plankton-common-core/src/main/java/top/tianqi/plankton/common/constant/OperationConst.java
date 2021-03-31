package top.tianqi.plankton.common.constant;

/**
 * 操作日志常量类型
 * @author Wukh
 * @create 2021-01-08
 */
public final class OperationConst {

    /*--------------------------- 数据操作类型 -----------------------------**/

    public static final String INSERT = "新增";
    public static final String UPDATE = "修改";
    public static final String DELETE = "删除";
    public static final String SELECT = "查询";
    public static final String LOGIN = "登录";
    public static final String LOG_OUT = "登出";

    /*--------------------------- 模块名称 --------------------------------**/

    /** 登录模块 */
    public static final String LOGIN_MODEL = "登录管理";

    /** 通知模块 */
    public static final String NOTICE_MODEL = "通知管理";

    /** 字典模块 */
    public static final String DICTIONARY_MODEL = "字典管理";

    /** 外置应用模块 */
    public static final String EXTERNAL_MODEL = "外置应用";

    /** 用户模块 */
    public static final String USER_MODEL = "用户管理";

    /** 版本模块 */
    public static final String VERSION_MODEL = "版本管理";

    /** 菜单模块 */
    public static final String MENU_MODEL = "菜单模块";

    /** 角色模块 */
    public static final String ROLE_MODEL = "角色模块";

    /** 权限模块 */
    public static final String AUTH_MODEL = "权限模块";

    /** 操作日志记录模块 */
    public static final String OPERATION_LOG_MODEL = "操作日志模块";

    /** 异常日志记录模块 */
    public static final String EXCEPTION_LOG_MODEL = "异常日志模块";

    /** Redis基本服务模块 */
    public static final String REDIS_MODEL = "Redis服务模块";

    /** 非会员模块 */
    public static final String NONMEMBER_MODEL = "非会员模块";

    /** 黑名单模块 */
    public static final String BLACKLIST_MODEL = "黑名单模块";
}
