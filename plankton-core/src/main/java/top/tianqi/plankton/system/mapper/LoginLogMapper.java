package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.tianqi.plankton.system.entity.LoginLog;

import java.util.List;
import java.util.Map;

/**
 * 登录日志Mapper
 * @author Wukh
 * @create 2021-03-20
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    List<Map<String, Object>> findLastSevenDaysVisitCount(String code);

    Integer findTodayIp();
}
