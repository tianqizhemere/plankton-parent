package top.tianqi.plankton.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类实体
 * @author tianQi
 * @create 2021-01-07
 */
public class BaseEntity extends Model<BaseEntity> implements Serializable {

    private static final long serialVersionUID = 8287109204790484232L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 创建时间 */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**修改时间*/
    @TableField(value = "modify_time", update = "now()", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public BaseEntity(){super();}

    public BaseEntity(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
