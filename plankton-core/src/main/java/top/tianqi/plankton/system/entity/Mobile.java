package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 手机信息
 * @author Wukh
 * @create 2021-01-08
 */
@TableName(value = "mobile")
public class Mobile extends BaseEntity {
    private static final long serialVersionUID = -5353397178787186262L;
    
    /** 手机型号 */
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
