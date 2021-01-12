package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.mapper.ExceptionLogDao;
import top.tianqi.plankton.system.service.ExceptionLogService;

/**
 * 操作日志异常信息服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "exceptionLogServiceImpl")
public class ExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLogDao, ExceptionLog> implements ExceptionLogService {
}
