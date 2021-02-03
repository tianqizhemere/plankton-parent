package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import java.util.List;

/**
 * 菜单
 * @author Wukh
 * @create 2021-01-23
 */
@TableName(value = "menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 2081900168971018113L;

    /** 访问路径 */
    @TableField
    private String url;

    /** 模块名称 */
    @TableField
    private String name;

    /** icon图标 */
    @TableField
    private String icon;

    /** 组件 */
    @TableField
    private String component;

    /** 父节点id */
    @TableField(value = "parent_id")
    private Long parentId;

    /** 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit) */
    @TableField
    private String perms;

    /** '类型   0：目录   1：菜单   2：按钮 */
    private Integer type;

    /** 子节点 */
    @TableField(exist = false)
    private List<Menu> children;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
