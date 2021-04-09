package top.tianqi.plankton.core.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.controller.BaseController;
import top.tianqi.plankton.core.system.entity.OperationLog;
import top.tianqi.plankton.core.system.service.OperationLogService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 操作日志controller
 * @author Wukh
 * @create 2021-01-24
 */
@RestController
@RequestMapping("/system/operationLog")
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
    @GetMapping("list")
    public Result list(String type, String name,Date beginTime, Date endTime){
        Page<OperationLog> page = operationLogService.getPage(type, name, beginTime, endTime, getPage());
        return SUCCESS_MESSAGE(page);
    }
}
