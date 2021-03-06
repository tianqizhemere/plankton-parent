package top.tianqi.plankton.notice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.notice.entity.Notice;

/**
 * 通知服务层接口
 * @author Wukh
 * @create 2021-02-21
 */
public interface NoticeService extends BaseService<Notice> {

    /**
     * 根据用户获取通知
     * @param model 型号
     * @param version 版本号
     * @return Notice 通知
     */
    Notice getUserNotice(String model, String version);

    /**
     * 加载数据列表
     * @param title 标题
     * @param page 分页对象
     * @return Page<Notice>
     */
    IPage<Notice> getPage(String title, IPage<Notice> page);
}
