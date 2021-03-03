package top.tianqi.plankton.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.admin.service.LoginStatisticsService;
import top.tianqi.plankton.admin.vo.LoginStatisticsVO;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 统计登录相关信息controller
 * @author Wukh
 * @create 2021-03-03
 */
@RestController
@RequestMapping(value = "/admin/loginStatistics")
public class LoginStatisticsController extends BaseController {

    @Resource(name = "loginStatisticsServiceImpl")
    private LoginStatisticsService loginStatisticsService;

    /**
     * 统计每日的总登录次数
     * @return Result 前端提示信息
     */
    @GetMapping(value = "/statisticsLogin")
    public Result statisticsLogin() throws ParseException {
        List<LoginStatisticsVO> list = loginStatisticsService.statisticsLogin();
        return SUCCESS_MESSAGE(list);
    }

}
