package top.tianqi.plankton.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.system.entity.ExceptionLog;
import top.tianqi.plankton.core.system.mapper.ExceptionLogMapper;
import top.tianqi.plankton.web.system.service.ExceptionLogService;

/**
 * 操作日志异常信息服务层实现
 *
 * @author Wukh
 * @create 2021-01-08
 */
@Service
public class ExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public Page<ExceptionLog> findPage(String name, Page<ExceptionLog> page) {
        QueryWrapper<ExceptionLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(StringUtils.isNotBlank(name), ExceptionLog::getErrorMessage, name);
        queryWrapper.lambda().orderByDesc(ExceptionLog::getCreateTime);
        return exceptionLogMapper.findPage(page, queryWrapper);
    }
}
