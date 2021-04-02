package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.controller.BaseController;
import top.tianqi.plankton.system.service.LoginLogService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录日志
 * @author Wukh
 * @create 2021-03-20
 */
@RestController
@RequestMapping("/system/loginLog")
public class LoginLogController extends BaseController {

    @Resource(name = "loginLogServiceImpl")
    private LoginLogService loginLogService;

    /**
     * 统计访问次数
     * @param code 用户唯一标识
     * @return Result 前端提示信息
     */
    @GetMapping("visitCount")
    public Result visitCount(String code){
        Map<String, Object> data = new HashMap<>();
        // 获取系统访问记录
        Integer totalVisitCount = loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Integer todayVisitCount = loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Integer todayIp = loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = loginLogService.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        List<Map<String, Object>> lastSevenUserVisitCount = loginLogService.findLastSevenDaysVisitCount(code);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return SUCCESS_MESSAGE(data);
    }

}
