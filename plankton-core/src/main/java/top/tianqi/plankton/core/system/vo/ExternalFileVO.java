package top.tianqi.plankton.core.system.vo;

/**
 * 外置文件VO
 * @author Wukh
 * @create 2021-02-01
 */
public class ExternalFileVO {

    /** 应用名称 */
    private String name;

    /** 版本编号 */
    private String versionCode;

    /** 下载路径 */
    private String downloadUrl;

    /** code值 */
    private Integer code;

    /** 类型名称 */
    private String typeName;

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
