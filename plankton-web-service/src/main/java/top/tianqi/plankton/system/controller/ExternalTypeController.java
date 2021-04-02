package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.controller.BaseController;
import top.tianqi.plankton.system.enumeration.ExternalTypeEnum;

/**
 * 外置应用类型controller
 * @author Wukh
 * @create 2021-02-01
 */
@RestController
@RequestMapping("/system/externalType")
public class ExternalTypeController extends BaseController {

    @GetMapping("list")
    public Result list(){
        return SUCCESS_MESSAGE(ExternalTypeEnum.values());
    }
}
