package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户实体
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** UUID */
    private String code;

    /** 设备型号 */
    @NotBlank(message = "设备型号不能为空")
    private String model;

    /** 用户状态，normal 普通用户 powerful专业 */
    private String userMode;

    /** 版本编号 */
    private String versionCode;

    /** 下载次数 */
    private Integer uploadCounter = 2;

    /** 是否被禁用 0:禁用，1：未禁用*/
    private Boolean isEnable = true;

    /** 用户最后登录时间 */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /** 用户来源 0 或1 */
    private Integer source;

    /** 手机号码 */
    private String phone;

    /** QQ */
    private String qq;

    /** 令牌 */
    @TableField(exist = false)
    private String authorization;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getUploadCounter() {
        return uploadCounter;
    }

    public void setUploadCounter(Integer uploadCounter) {
        this.uploadCounter = uploadCounter;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getEnableStatus(){
        if (this.isEnable) {
            return "启用";
        }
        return "禁用";
    }
}
