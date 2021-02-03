package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 角色-菜单中间表
 * @author Wukh
 * @create 2021-01-04
 */
@TableName("role_menu")
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 角色id*/
    private Long roleId;

    /** 菜单id */
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
