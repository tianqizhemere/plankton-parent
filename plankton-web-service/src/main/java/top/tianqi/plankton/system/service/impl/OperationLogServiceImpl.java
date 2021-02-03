package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.mapper.OperationLogMapper;
import top.tianqi.plankton.system.service.OperationLogService;

import java.util.Date;
import java.util.List;

/**
 * 操作日志服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "operationLogServiceImpl")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public PageResult getPage(String type, String name, Date beginTime, Date endTime, Page<OperationLog> page) {
        List<OperationLog> list = operationLogMapper.findPage(type, name, beginTime, endTime, page);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), list);
    }
}
