package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户实体
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 手机型号 */
    private String model;

    /** 会员编号(设备识别号) */
    private String imel;

    /** 版本编号 */
    private String versionCode;

    /** 密码 */
    private String password;

    /** 下载次数 */
    private Integer uploadCounter = 2;

    private Integer isEnable;

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

    public Integer getUploadCounter() {
        return uploadCounter;
    }

    public void setUploadCounter(Integer uploadCounter) {
        this.uploadCounter = uploadCounter;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsEnable() {
        return isEnable;
    }
}
