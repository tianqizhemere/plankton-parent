package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

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
    /** 是否有更新 */
    @TableField(exist = false)
    private Integer isUpdate = 0;
    /** 是否升级 1升级，0不升级'*/
    private Integer type;

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

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
