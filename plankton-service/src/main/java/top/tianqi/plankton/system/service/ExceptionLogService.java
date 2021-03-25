package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.ExceptionLog;

/**
 * 操作日志异常信息服务层接口
 *
 * @author Wukh
 * @create 2021-01-08
 */
public interface ExceptionLogService extends BaseService<ExceptionLog> {

    /**
     * 加载数据列表
     *
     * @param name 异常信息
     * @param page 分页对象
     * @return Page<ExceptionLog>
     */
    Page<ExceptionLog> findPage(String name, Page<ExceptionLog> page);
}