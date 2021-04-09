package top.tianqi.plankton.core.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.core.BaseEntity;
import top.tianqi.plankton.core.system.enumeration.VersionTypeEnum;

/**
 * 通知
 * @author Wukh
 * @create 2021-02-21
 */
@TableName("notice")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 479412578968908422L;

    /** 标题 */
    private String title;

    /** 通知内容 */
    private String content;

    /** 发送数量 */
    private Integer sendCount;

    /** 读取数量 */
    private Integer readCount;

    /** 是否最新通知，0-旧通知，1-最新通知 */
    private Integer type = 1;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName(){
        if (this.type != null) {
            return VersionTypeEnum.value(this.type);
        }
        return null;
    }
}
