package top.tianqi.plankton.core.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.controller.BaseController;
import top.tianqi.plankton.core.system.enumeration.OperationTypeEnum;

/**
 * 数据操作类型controller
 * @author Wukh
 * @create 2021-02-04
 */
@RestController
@RequestMapping("/system/operationType")
public class OperationTypeController extends BaseController {

    @GetMapping("list")
    public Result list(){
        return SUCCESS_MESSAGE(OperationTypeEnum.values());
    }
}
