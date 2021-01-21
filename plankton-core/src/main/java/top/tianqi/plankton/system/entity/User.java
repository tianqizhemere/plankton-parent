package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
    @TableField(value = "user_mode")
    private String userMode;

    /** 版本编号 */
    @TableField(value = "version_code")
    private String versionCode;

    /** 下载次数 */
    @TableField(value = "upload_Counter")
    private Integer uploadCounter = 2;

    /** 是否被禁用 0:禁用，1：未禁用*/
    @TableField(value = "is_enable")
    private Boolean isEnable = true;

    /** 用户最后登录时间 */
    private Date loginTime;

    /** 用户来源 0 或1 */
    private Integer source;

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

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
