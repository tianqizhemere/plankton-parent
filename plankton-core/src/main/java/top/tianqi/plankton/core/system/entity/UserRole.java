package top.tianqi.plankton.core.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.core.BaseEntity;

/**
 * 用户-角色中间表
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 角色id*/
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
