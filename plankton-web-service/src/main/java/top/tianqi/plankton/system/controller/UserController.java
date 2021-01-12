package top.tianqi.plankton.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author tianQi
 * @create 2021-01-07
 */
@RestController
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 加载用户列表
     * @return Result 前端提示信息
     */
    @OperLog(operationModel = "用户管理", operationDesc = "查询用户列表", operationType = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(){
        return SUCCESS_MESSAGE();
    }

    /**
     * 登录
     * @param ieml 会员编号
     * @return Result
     */
    @OperLog(operationModel = "用户管理", operationDesc = "用户登录", operationType = OperationConst.SELECT)
    @PostMapping(value = "/login")
    public Result login(String ieml) throws UnsupportedEncodingException {
        User user = userService.getUser(ieml);
        if (user == null) {
            throw new BusinessException("登录失败，ieml不存在或错误");
        }
        String token = JwtUtil.sign(ieml, user.getVersionCode());
        return Result.success(token);
    }

    /**
     * 未授权
     * @return Result
     */
    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return new Result(401, "未授权");
    }
}
