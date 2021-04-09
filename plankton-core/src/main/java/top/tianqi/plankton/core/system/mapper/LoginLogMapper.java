package top.tianqi.plankton.core.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.tianqi.plankton.core.system.entity.LoginLog;

import java.util.List;
import java.util.Map;

/**
 * 登录日志Mapper
 * @author Wukh
 * @create 2021-03-20
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 获取系统近七天来的访问记录
     * @param code 登录用户code
     * @return 系统近七天来的访问记录
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(String code);

    /**
     * 统计今日登陆ip数量
     * @return
     */
    Integer findTodayIp();
}
