package top.tianqi.plankton.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.system.entity.ExceptionLog;
import top.tianqi.plankton.system.service.ExceptionLogService;

import javax.annotation.Resource;

/**
 * 异常日志controller
 * @author Wukh
 * @create 2021-02-23
 */
@RestController
@RequestMapping(value = "/system/exceptionLog")
public class ExceptionLogController extends BaseController {

    @Resource(name = "exceptionLogServiceImpl")
    private ExceptionLogService exceptionLogService;

    @GetMapping(value = "/page")
    public Result findPage(){
        Page<ExceptionLog> page = exceptionLogService.findPage(getPage());
        return SUCCESS_MESSAGE(page);
    }
}
