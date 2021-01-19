package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.mapper.OperationLogMapper;
import top.tianqi.plankton.system.service.OperationLogService;

/**
 * 操作日志服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "operationLogServiceImpl")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
