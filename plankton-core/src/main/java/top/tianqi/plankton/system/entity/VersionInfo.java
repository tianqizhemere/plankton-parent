package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

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
    private String versionCode;

    /** 下载路径 */
    private String downloadUrl;

    /** 版本描述 */
    private String versionDesc;

    /** 手机型号 */
    @NotBlank(message = "手机型号不能为空")
    private String model;

    /** 是否有更新 0:无更新，1：有更新*/
    @TableField(exist = false)
    private Boolean isUpdate = Boolean.FALSE;

    /** 是否升级 1升级，0不升级'*/
    private Integer type = 1;

    /** 附件id */
    @TableField(exist = false)
    private String attachIds;

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

    public String getTypeName(){
        if (this.type == 1){
            return "最新版本";
        }
        return "历史版本";
    }
}
