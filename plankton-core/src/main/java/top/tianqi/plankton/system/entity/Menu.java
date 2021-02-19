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
@TableName("menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 2081900168971018113L;

    /** 访问路径 */
    private String url;

    /** 模块名称 */
    private String name;

    /** icon图标 */
    private String icon;

    /** 组件 */
    private String component;

    /** 父节点id */
    private Long parentId;

    /** 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit) */
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
