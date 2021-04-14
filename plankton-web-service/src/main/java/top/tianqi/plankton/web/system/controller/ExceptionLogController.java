package top.tianqi.plankton.web.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.core.system.entity.ExceptionLog;
import top.tianqi.plankton.web.system.service.ExceptionLogService;

import javax.annotation.Resource;

/**
 * 异常日志controller
 * @author Wukh
 * @create 2021-02-23
 */
@RestController
@RequestMapping("/system/exceptionLog")
public class ExceptionLogController extends BaseController {

    @Resource(name = "exceptionLogServiceImpl")
    private ExceptionLogService exceptionLogService;

    /**
     * 加载数据列表
     * @param name 异常信息
     * @return Result 前端提示信息
     */
    @GetMapping("page")
    public Result findPage(String name){
        Page<ExceptionLog> page = exceptionLogService.findPage(name, getPage());
        return SUCCESS_MESSAGE(page);
    }
}
