package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.service.OperationLogService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 操作日志controller
 * @author Wukh
 * @create 2021-01-24
 */
@RestController
@RequestMapping(value = "/system/operationLog")
public class OperationLogController extends BaseController {

    @Resource(name = "operationLogServiceImpl")
    private OperationLogService operationLogService;


    /**
     * 加载数据列表
     * @param type 操作类型
     * @param beginTime 操作时间起
     * @param endTime 操作时间止
     * @return Result 前端提示信息
     */
    @GetMapping(value = "/list")
    public Result list(String type, Date beginTime, Date endTime){
        PageResult page = operationLogService.getPage(type, beginTime, endTime, getPage());
        return SUCCESS_MESSAGE(page);
    }
}
