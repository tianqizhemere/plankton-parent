package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Message;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;

/**
 * @author tianQi
 * @create 2021-01-07
 */
@RestController
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    /**
     * 加载用户列表
     * @return Message 前端提示信息
     */
    @OperLog(operationModel = "用户管理", operationDesc = OperationConst.SELECT, operationType = "查询用户列表")
    @GetMapping(value = "/list")
    public Message list(){
        return SUCCESS_MESSAGE;
    }
}
