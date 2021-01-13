package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
