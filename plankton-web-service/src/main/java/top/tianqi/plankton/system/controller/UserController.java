package top.tianqi.plankton.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    @RequiresPermissions("system:user:index")
    @OperLog(operationModel = "用户管理", operationDesc = "查询用户列表", operationType = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(String ieml, String username, Integer pageNo, Integer pageSize, HttpServletRequest request){
        Page<User> page = userService.getPage(ieml, username, getPage());
        return SUCCESS_MESSAGE();
    }


    @RequiresPermissions("system:user:save")
    @OperLog(operationModel = "用户管理", operationDesc = "新增用户", operationType = OperationConst.SELECT)
    @PostMapping(value = "/save")
    public Result save(@Valid User user, BindingResult result){
        User baseUser = userService.getUser(user.getImel());
        if (baseUser != null) {
            return new Result(1007, "imel已存在");
        }
        userService.insert(user);
        return SUCCESS_MESSAGE();
    }
}
