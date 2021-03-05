package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 非会员登录
 * @author Wukh
 * @create 2021-01-19
 */
@TableName("nonmember")
public class Nonmember extends BaseEntity {
    private static final long serialVersionUID = -4009251666832670319L;

    /** UUID */
    private String code;

    /** 设备型号 */
    @NotBlank(message = "设备型号不能为空")
    private String model;

    public Nonmember() {
    }

    public Nonmember(String code, String model) {
        this.code = code;
        this.model = model;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
