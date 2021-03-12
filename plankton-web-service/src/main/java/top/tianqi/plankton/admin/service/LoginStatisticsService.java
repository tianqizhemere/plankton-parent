package top.tianqi.plankton.admin.service;

import top.tianqi.plankton.admin.vo.LoginStatisticsVO;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.OperationLog;

import java.text.ParseException;
import java.util.List;

/**
 * 统计登录相关信息服务层接口
 * @author Wukh
 * @create 2021-03-03
 */
public interface LoginStatisticsService extends BaseService<OperationLog> {

    /**
     * 通过操作日志统计每月的登录次数
     * @return List<LoginStatisticsVO>
     */
    List<LoginStatisticsVO> statisticsLogin() throws ParseException;

}
