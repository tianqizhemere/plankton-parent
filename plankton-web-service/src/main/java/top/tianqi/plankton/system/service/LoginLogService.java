package top.tianqi.plankton.system.service;

import top.tianqi.plankton.common.service.BaseService;
import top.tianqi.plankton.system.entity.LoginLog;

import java.util.List;
import java.util.Map;

/**
 * 登录日志服务层接口
 *
 * @author Wukh
 * @create 2021-03-20
 */
public interface LoginLogService extends BaseService<LoginLog> {

    /**
     * 获取系统总访问次数
     *
     * @return Integer
     */
    Integer findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Integer findTodayVisitCount();

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Integer findTodayIp();

    /**
     * 获取系统近七天来的访问记录
     *
     * @return 系统近七天来的访问记录
     */
    List<Map<String, Object>> findLastSevenDaysVisitCount(String code);
}
