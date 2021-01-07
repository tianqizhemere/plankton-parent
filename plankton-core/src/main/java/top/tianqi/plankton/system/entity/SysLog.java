package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户的操作日志
 * @author tianQi
 * @create 2021-01-07
 */
@TableName(value = "sys_log")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 3513566928111474788L;

    /** 用户名 */
    private String username;
    /** 用户ip */
    private String userIp;
    /** 请求方法 */
    private String requestMethod;
    /** 方法描述 */
    private String requestDesc;
    /** 请求参数 */
    private String params;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestDesc() {
        return requestDesc;
    }

    public void setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
