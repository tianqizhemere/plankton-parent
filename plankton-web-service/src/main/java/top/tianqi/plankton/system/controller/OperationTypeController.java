package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.system.enumeration.OperationTypeEnum;

/**
 * 数据操作类型controller
 * @author Wukh
 * @create 2021-02-04
 */
@RestController
@RequestMapping(value = "/system/operationType")
public class OperationTypeController extends BaseController {

    @GetMapping(value = "/list")
    public Result list(){
        return SUCCESS_MESSAGE(OperationTypeEnum.values());
    }
}
