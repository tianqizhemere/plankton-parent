package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public Page<ExceptionLog> findPage(Page<ExceptionLog> page) {
        QueryWrapper<ExceptionLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(ExceptionLog::getCreateTime);
        return exceptionLogMapper.findPage(page, queryWrapper);
    }
}
