package top.tianqi.plankton.notice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.notice.entity.Notice;
import top.tianqi.plankton.notice.mapper.NoticeMapper;
import top.tianqi.plankton.notice.service.NoticeService;

/**
 * @author Wukh
 * @create 2021-02-21
 */
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Notice getUserNotice(String model, String version) {
        return null;
    }

    @Override
    public Page<Notice> getPage(String title, Page<Notice> page) {
        return null;
    }
}
