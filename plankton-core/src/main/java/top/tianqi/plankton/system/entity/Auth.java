package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户权限
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("auth")
public class Auth extends BaseEntity  {
    private static final long serialVersionUID = 1L;

    /**
     * 权限名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
