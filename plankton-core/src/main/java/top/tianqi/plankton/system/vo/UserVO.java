package top.tianqi.plankton.system.vo;

import top.tianqi.plankton.system.entity.UserConfig;

import java.util.Set;

/**
 * 用户VO
 * @author Wukh
 * @create 2021-01-17
 */
public class UserVO {

    /** 用户id */
    private Long id;

    /** code */
    private String code;

    /** 设备型号 */
    private String model;

    /** 用户类型 */
    private String userMode;

    /** 是否启用 */
    private Boolean isEnable;

    /** 令牌 */
    private String authorization;

    /** token过期时间 */
    private Integer expireTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
