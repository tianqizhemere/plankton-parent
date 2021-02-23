package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.ExceptionLog;

/**
 * 操作日志异常信息服务层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface ExceptionLogService extends BaseService<ExceptionLog> {

    /**
     * 加载数据列表
     * @param page 分页对象
     * @return Page<ExceptionLog>
     */
    Page<ExceptionLog> findPage(Page<ExceptionLog> page);
}
