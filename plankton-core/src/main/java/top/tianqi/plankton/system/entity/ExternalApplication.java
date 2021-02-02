package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.system.enumeration.ExternalTypeEnum;
import top.tianqi.plankton.system.enumeration.VersionTypeEnum;

import javax.validation.constraints.NotBlank;

/**
 * 外置应用
 * @author Wukh
 * @create 2021-02-01
 */
@TableName(value = "external_application")
public class ExternalApplication extends BaseEntity {
    private static final long serialVersionUID = 4310128748575456556L;

    /** 应用名称 */
    @NotBlank(message = "应用名称不能为空")
    private String name;

    /** 应用版本编号 */
    @NotBlank(message = "版本编号不能为空")
    private String versionCode;

    /** 应用描述 */
    private String versionDesc;

    /** 附件id */
    @TableField(exist = false)
    private String attachIds;

    /** 下载路径 */
    private String downloadUrl;

    /** 应用类型, 详见ExternalTypeEnum枚举类 */
    private Integer externalType;

    /** 是否升级 1升级，0不升级'*/
    private Integer type = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getAttachIds() {
        return attachIds;
    }

    public void setAttachIds(String attachIds) {
        this.attachIds = attachIds;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getExternalType() {
        return externalType;
    }

    public void setExternalType(Integer externalType) {
        this.externalType = externalType;
    }

    public String getTypeName(){
        if (this.type == 1){
            return VersionTypeEnum.value(this.type);
        }
        return null;
    }

    private String getFileType(){
        if (this.externalType != null) {
            return ExternalTypeEnum.value(this.externalType);
        }
        return null;
    }
}
