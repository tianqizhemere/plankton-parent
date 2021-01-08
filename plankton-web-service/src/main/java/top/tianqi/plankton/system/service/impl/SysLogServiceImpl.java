package top.tianqi.plankton.system.service.impl;

import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.SuperService;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.mapper.SysLogDao;
import top.tianqi.plankton.system.service.SysLogService;

/**
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "sysLogServiceImpl")
public class SysLogServiceImpl extends SuperService<SysLogDao, OperationLog> implements SysLogService {
}
