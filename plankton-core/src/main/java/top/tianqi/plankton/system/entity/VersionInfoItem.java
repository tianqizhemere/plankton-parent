package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 版本信息明细
 * @author tianQi
 * @create 2021-01-05
 */
@TableName("version_upgrade_item")
public class VersionInfoItem extends BaseEntity {

    private static final long serialVersionUID = 3577514419817872789L;

    /** 版本主键id */
    private Long versionInfoId;

    /** 版本编号 */
    private String versionCode;

    /** 手机型号 */
    private String model;

    public Long getVersionInfoId() {
        return versionInfoId;
    }

    public void setVersionInfoId(Long versionInfoId) {
        this.versionInfoId = versionInfoId;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
