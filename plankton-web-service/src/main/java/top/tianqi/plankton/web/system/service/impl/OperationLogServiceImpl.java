package top.tianqi.plankton.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.core.system.entity.OperationLog;
import top.tianqi.plankton.core.system.mapper.OperationLogMapper;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.web.system.service.OperationLogService;

import java.util.Date;

/**
 * 操作日志服务层实现
 *
 * @author Wukh
 * @create 2021-01-08
 */
@Service
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public Page<OperationLog> getPage(String type, String name, Date beginTime, Date endTime, Page<OperationLog> page) {
        LambdaQueryWrapper<OperationLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(type), OperationLog::getType, type);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(name), OperationLog::getModel, name);
        lambdaQueryWrapper.orderByDesc(OperationLog::getCreateTime);
        return operationLogMapper.selectPage(page, lambdaQueryWrapper);
    }
}
