package top.tianqi.plankton.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.tianqi.plankton.base.entity.BaseEntity;

/**
 * 用户-通知中间表
 * @author Wukh
 * @create 2021-02-21
 */
@TableName("user_notice")
public class UserNotice extends BaseEntity {

    /** 通知id */
    private Long noticeId;

    /** 用户id */
    private Long userId;

    /** 是否读取, 0-未读，1-已读 */
    private Integer isRead;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
