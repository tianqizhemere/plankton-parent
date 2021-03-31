package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import java.util.Date;

/**
 * 登录日志
 * @author Wukh
 * @create 2021-03-20
 */
@TableName("login_log")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 4308980614059058648L;

    /** 登录code */
    private String code;

    /** 登录时间 */
    private Date loginTime;

    /** 登录地点 */
    private String location;

    /** 登录ip */
    private String ip;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
