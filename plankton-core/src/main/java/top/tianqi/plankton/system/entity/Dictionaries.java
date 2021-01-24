package top.tianqi.plankton.system.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 字典
 * @author Wukh
 * @create 2021-01-24
 */
@TableName(value = "dictionaries")
public class Dictionaries extends BaseEntity {
    private static final long serialVersionUID = -5201090437613680914L;

    /** 数据值 */
    private String value;

    /** 标签名 */
    private String label;

    /** 类型 */
    private String type;

    /** 描述 */
    private String description;

    /** 排序（升序） */
    private Long sort;

    /** 备注信息 */
    private String remarks;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
