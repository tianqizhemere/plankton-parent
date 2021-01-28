package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.OperationLog;

import java.util.Date;

/**
 * 操作日志服务层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface OperationLogService extends BaseService<OperationLog> {

    /**
     * 加载数据列表
     * @param type 操作类型
     * @param beginTime 操作时间起
     * @param endTime 操作时间止
     * @param page 分页对象
     * @return PageResult 前端提示信息
     */
    PageResult getPage(String type, String name, Date beginTime, Date endTime, Page<OperationLog> page);
}
