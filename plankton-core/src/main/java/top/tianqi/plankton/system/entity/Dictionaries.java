package top.tianqi.plankton.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 字典
 * @author Wukh
 * @create 2021-01-24
 */
@TableName("dictionaries")
public class Dictionaries extends BaseEntity {
    private static final long serialVersionUID = -5201090437613680914L;

    /** 数据值 */
    @NotBlank(message = "名称不能为空")
    private String name;

    /** 上级字典ID，一级字典为0 */
    @TableField(value = "parent_id")
    @NotNull(message = "父节点id不能为空")
    private Long parentId;

    /** 排序 */
    @TableField(value = "order_num")
    private Integer orderNum;

    /** 删除状态（0，正常，1已删除） */
    @TableField(value = "del_flag")
    private Integer delFlag;

    /** 父节点名称 */
    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Integer level;

    /** 子节点 */
    @TableField(exist = false)
    private List<Dictionaries> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Dictionaries> getChildren() {
        return children;
    }

    public void setChildren(List<Dictionaries> children) {
        this.children = children;
    }
}
