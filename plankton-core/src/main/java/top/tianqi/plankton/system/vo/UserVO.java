package top.tianqi.plankton.system.vo;

/**
 * 用户VO
 * @author Wukh
 * @create 2021-01-17
 */
public class UserVO {

    /** 用户id */
    private Long id;

    /** 设备型号 */
    private String model;

    /** 用户类型 */
    private String userMode;

    /** 是否启用 */
    private Boolean isEnable;

    /** 令牌 */
    private String Authorization;

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
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        this.Authorization = authorization;
    }
}
