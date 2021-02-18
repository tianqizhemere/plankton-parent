package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.mapper.ExceptionLogMapper;
import top.tianqi.plankton.system.service.ExceptionLogService;

/**
 * 操作日志异常信息服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service
public class ExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {
}
