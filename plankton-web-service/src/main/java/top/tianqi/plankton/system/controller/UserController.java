package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Message;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.base.annotation.aop.Operation;

/**
 * @author tianQi
 * @create 2021-01-07
 */
@RestController
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    @Operation(value = "获取用户列表")
    @GetMapping(value = "/list")
    public Message list(){
        return SUCCESS_MESSAGE;
    }
}
