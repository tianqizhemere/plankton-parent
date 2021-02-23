package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.system.enumeration.VersionTypeEnum;

import javax.validation.constraints.NotBlank;

/**
 * 版本信息
 * @author tianQi
 * @create 2021-01-05
 */
@TableName("version_upgrade")
public class VersionInfo extends BaseEntity {

    private static final long serialVersionUID = 3577514419817872789L;

    /** 版本编号 */
    @NotBlank(message = "版本编号不能为空")
    private String versionCode;

    /** 下载路径 */
    private String downloadUrl;

    /** 版本描述 */
    private String versionDesc;

    /** 手机型号 */
    @NotBlank(message = "手机型号不能为空")
    private String model;

    /** 是否升级 1升级，0不升级'*/
    private Integer type = 1;

    /** 是否有更新 0:无更新，1：有更新*/
    @TableField(exist = false)
    private Boolean isUpdate = Boolean.FALSE;

    /** 附件id */
    @TableField(exist = false)
    private String attachIds;

    /** 是否为大版本更新 */
    @TableField(exist = false)
    private Boolean maxVersion = Boolean.FALSE;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAttachIds() {
        return attachIds;
    }

    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    public Boolean getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(Boolean maxVersion) {
        this.maxVersion = maxVersion;
    }

    public String getTypeName(){
        if (this.type != null){
            return VersionTypeEnum.value(this.type);
        }
        return null;
    }
}
