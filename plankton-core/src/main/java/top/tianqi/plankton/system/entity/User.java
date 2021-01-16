package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;

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

    /** 设备识别号 */
    private String model;

    /** 设备型号 */
    @NotBlank(message = "设备型号不能为空")
    @TableField(value = "phone_model")
    private String phoneModel;

    /** 用户状态，normal 普通用户 powerful专业 */
    @TableField(value = "user_mode")
    private String userMode;

    /** 版本编号 */
    @TableField(value = "version_code")
    private String versionCode;

    /** 下载次数 */
    @TableField(value = "upload_Counter")
    private Integer uploadCounter = 2;

    /** 是否被禁用 */
    private Integer isEnable = 1;

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

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getUploadCounter() {
        return uploadCounter;
    }

    public void setUploadCounter(Integer uploadCounter) {
        this.uploadCounter = uploadCounter;
    }
}
