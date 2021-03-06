package top.tianqi.plankton.core.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.core.BaseEntity;

/**
 * 角色
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = -2149389089173706205L;
    /** 角色名称 */
    private String name;

    /** 角色描述 */
    private String remark;

    /** 启用状态，1：启用，0：未启用 */
    private Integer enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
