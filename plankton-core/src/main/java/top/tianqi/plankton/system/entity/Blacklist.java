package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 黑名单
 * @author Wukh
 * @create 2021-03-11
 */
@TableName("blacklist")
public class Blacklist extends BaseEntity {

    private static final long serialVersionUID = 2476438859930288464L;

    /** 用户的真实ip */
    private String ip;

    /** code */
    private String code;

    /** 是否为黑名单用户 */
    private Boolean isEnable;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
