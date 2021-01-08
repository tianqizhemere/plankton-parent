package top.tianqi.plankton.system.entity;

import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 手机信息
 * @author Wukh
 * @create 2021-01-08
 */
public class Mobile extends BaseEntity {
    private static final long serialVersionUID = -5353397178787186262L;
    
    /** 手机型号 */
    @NotBlank(message = "手机型号不能为空")
    private String model;

    /** 会员编号 */
    private String imel;

    /** 版本编号 */
    private String versionCode;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}