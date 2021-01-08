package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Message;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;

/**
 * @author tianQi
 * @create 2021-01-07
 */
@RestController
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    @OperLog(operationModel = "用户管理", operationDesc = "")
    @GetMapping(value = "/list")
    public Message list(){
        return SUCCESS_MESSAGE;
    }
}
