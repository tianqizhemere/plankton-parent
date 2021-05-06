package top.tianqi.plankton.core.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import top.tianqi.plankton.core.common.annotation.Desensitization;
import top.tianqi.plankton.core.BaseEntity;
import top.tianqi.plankton.core.common.annotation.excel.ExcelColumn;
import top.tianqi.plankton.core.common.enumeration.DesensitizationTypeEnum;
import top.tianqi.plankton.core.system.enumeration.EnableStatusEnum;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户实体
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = -2369578080352985398L;

    /** UUID */
    @ExcelColumn(order = 0, title = "CODE", column = 50)
    private String code;

    /** 设备型号 */
    @ExcelColumn(order = 1, title = "设备型号", column = 50)
    @NotBlank(message = "设备型号不能为空")
    private String model;

    /** 用户状态，normal 普通用户 powerful专业 */
    @ExcelColumn(order = 2, title = "用户类型", column = 50)
    private String userMode;

    /** 版本编号 */
    private String versionCode;

    /** 下载次数 */
    private Integer uploadCounter;

    /** 是否被禁用 0:禁用，1：未禁用*/
    @ExcelColumn(order = 4, title = "是否启用", column = 20)
    private Boolean isEnable = true;

    /** 用户最后登录时间 */
    @ExcelColumn(order = 3, title = "最后登录时间", column = 50)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /** 用户来源 0 或 1 */
    private Integer source;

    /** 手机号码 */
    @Desensitization(type = DesensitizationTypeEnum.PHONE)
    private String phone;

    /** QQ */
    private String qq;

    /** 登录ip */
    private String ip;

    /** 登录城市 */
    private String address;

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
        return EnableStatusEnum.value(this.isEnable);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
