package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 角色-权限关联表
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("role_auth")
public class RoleAuth extends BaseEntity {

    private static final long serialVersionUID = -2464151187032249167L;

    /** 角色 id*/
    private Long roleId;

    /** 权限 id */
    private Long authId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}
