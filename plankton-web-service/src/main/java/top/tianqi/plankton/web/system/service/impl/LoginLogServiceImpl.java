package top.tianqi.plankton.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.web.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.core.common.utils.DateUtils;
import top.tianqi.plankton.core.system.entity.LoginLog;
import top.tianqi.plankton.core.system.mapper.LoginLogMapper;
import top.tianqi.plankton.web.system.service.LoginLogService;

import java.util.List;
import java.util.Map;

/**
 * 登录日志服务层实现
 *
 * @author Wukh
 * @create 2021-03-20
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Integer findTotalVisitCount() {
        return loginLogMapper.selectCount(null);
    }

    @Override
    public Integer findTodayVisitCount() {
        LambdaQueryWrapper<LoginLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ge(LoginLog::getLoginTime, DateUtils.getDayBegin());
        lambdaQueryWrapper.le(LoginLog::getLoginTime, DateUtils.getDayEnd());
        return loginLogMapper.selectCount(lambdaQueryWrapper);
    }

    @Override
    public Integer findTodayIp() {
        return loginLogMapper.findTodayIp();
    }

    @Override
    public List<Map<String, Object>> findLastSevenDaysVisitCount(String code) {
        return loginLogMapper.findLastSevenDaysVisitCount(code);
    }
}
