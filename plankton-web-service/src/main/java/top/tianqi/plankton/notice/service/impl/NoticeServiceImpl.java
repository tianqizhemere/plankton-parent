package top.tianqi.plankton.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.notice.entity.Notice;
import top.tianqi.plankton.notice.entity.UserNotice;
import top.tianqi.plankton.notice.mapper.NoticeMapper;
import top.tianqi.plankton.notice.mapper.UserNoticeMapper;
import top.tianqi.plankton.notice.service.NoticeService;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserMapper;

import java.util.List;

/**
 * 通知服务层实现
 * @author Wukh
 * @create 2021-02-21
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserNoticeMapper userNoticeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Notice getUserNotice(String model, String version) {
        User currentUser = getCurrentUser();
        Long userId = currentUser.getId();
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("un.user_id", userId);
        queryWrapper.eq("n.type", 1);
        queryWrapper.eq("un.is_read", 0);
        List<Notice> notices = noticeMapper.findUserNotice(queryWrapper);
        if (!CollectionUtils.isEmpty(notices)) {
            Notice notice = notices.get(0);
            notice.setReadCount(notice.getReadCount() + 1);

            LambdaQueryWrapper<UserNotice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserNotice::getNoticeId, notice.getId());
            lambdaQueryWrapper.eq(UserNotice::getUserId, userId);
            List<UserNotice> userNotices = userNoticeMapper.selectList(lambdaQueryWrapper);
            for (UserNotice userNotice : userNotices) {
                userNotice.setIsRead(1);
                userNoticeMapper.updateById(userNotice);
            }
            return notice;
        }
        return null;
    }

    @Override
    public Page<Notice> getPage(String title, Page<Notice> page) {
        return null;
    }

    @Override
    public boolean save(Notice notice) {
        // 设置之前的通知为旧通知
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Notice::getType, 1);
        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(notices)) {
            for (Notice baseNotice : notices) {
                baseNotice.setType(0);
                noticeMapper.updateById(baseNotice);
            }
        }
        boolean result = super.save(notice);
        List<User> users = userMapper.selectList(null);
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                UserNotice userNotice = new UserNotice();
                userNotice.setIsRead(0);
                userNotice.setUserId(user.getId());
                userNotice.setNoticeId(notice.getId());
                userNoticeMapper.insert(userNotice);
            }
        }
        notice.setSendCount(users.size());
        notice.setReadCount(0);
        noticeMapper.selectById(notice);
        return result;
    }
}
