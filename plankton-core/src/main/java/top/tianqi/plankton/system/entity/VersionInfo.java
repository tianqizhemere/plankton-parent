package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * 版本信息
 * @author tianQi
 * @create 2021-01-05
 */
@TableName("version_info")
public class VersionInfo {
    /** 主键 */
    private Integer id;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifiedTime;
    /** 版本编号 */
    private Integer versionCode;
    /** 版本号 */
    private String version;
    /** 下载路径 */
    private String downloadUrl;
    /** 版本描述 */
    private String versionDesc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
}
