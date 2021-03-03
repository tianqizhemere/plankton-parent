package top.tianqi.plankton.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.admin.mapper.LoginStatisticsMapper;
import top.tianqi.plankton.admin.service.LoginStatisticsService;
import top.tianqi.plankton.admin.vo.LoginStatisticsVO;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.utils.DateUtils;
import top.tianqi.plankton.system.entity.OperationLog;
import top.tianqi.plankton.system.mapper.OperationLogMapper;

import java.text.ParseException;
import java.util.*;

/**
 * 统计登录相关信息服务层实现
 * @author Wukh
 * @create 2021-03-03
 */
@Service
public class LoginStatisticsServiceImpl extends BaseServiceImpl<LoginStatisticsMapper, LoginStatisticsVO> implements LoginStatisticsService {

    @Autowired
    private LoginStatisticsMapper loginStatisticsMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public List<LoginStatisticsVO> statisticsLogin() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = DateUtils.stringToDate(DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME) + " 00:00:00");
        Date endTime = DateUtils.stringToDate(calendar.get(Calendar.YEAR) +"-05-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date beginTime = DateUtils.addMonths(endTime, -5);
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(currentTime);
        endCalendar.setTime(endTime);
        // 当前时间与结束时间的月份之差
        int result = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        if (result <= 0) {
            endTime = DateUtils.addMonths(endTime, Math.abs(result) + 1);
            beginTime = DateUtils.addMonths(beginTime, Math.abs(result) + 1);
        }
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ge(OperationLog::getCreateTime, beginTime);
        queryWrapper.lambda().lt(OperationLog::getCreateTime, endTime);
        queryWrapper.lambda().eq(OperationLog::getType, OperationConst.LOGIN);
        List<OperationLog> list = operationLogMapper.selectList(queryWrapper);

        Map<String, LoginStatisticsVO> map = new LinkedHashMap<>();
        while(beginTime.before(endTime)){
            String key = DateUtil.format(beginTime, "yyyy-MM");
            map.put(key, new LoginStatisticsVO(key, 0));
            beginTime = DateUtils.addMonths(beginTime, 1);
        }
        if (!CollectionUtils.isEmpty(list)) {
            for (OperationLog operationLog : list) {
                String key = DateUtil.format(operationLog.getCreateTime(), "yyyy-MM");
                Date begin = DateUtils.getFirstDayOfMonth(operationLog.getCreateTime());
                Date end = DateUtils.getLastDayOfMonth(operationLog.getCreateTime());
                QueryWrapper<OperationLog> countQueryWrapper = new QueryWrapper<>();
                countQueryWrapper.lambda().ge(OperationLog::getCreateTime, begin);
                countQueryWrapper.lambda().lt(OperationLog::getCreateTime, end);
                countQueryWrapper.lambda().eq(OperationLog::getType, OperationConst.LOGIN);
                Integer count = operationLogMapper.selectCount(countQueryWrapper);
                if (map.get(key) != null) {
                    LoginStatisticsVO loginStatisticsVO = map.get(key);
                    loginStatisticsVO.setCount(count);
                    map.put(key, loginStatisticsVO);
                }
            }
        }
        return new LinkedList<>(map.values());
    }
}
