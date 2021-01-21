package top.tianqi.plankton.system.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户controller
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
    //@RequiresPermissions("system:user:index")
    @OperLog(operationModel = "用户管理", operationDesc = "查询用户列表", operationType = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(String ieml, String username){
        List<User> page = userService.getPage(ieml, username, getPage());
        return SUCCESS_MESSAGE(page);
    }


    //@RequiresPermissions("system:user:save")
    @OperLog(operationModel = "用户管理", operationDesc = "新增用户", operationType = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid User user, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        User baseUser = userService.getUser(user.getCode());
        if (baseUser != null) {
            return Result.error(1007, "uuid已存在");
        }
        userService.insert(user);
        return SUCCESS_MESSAGE();
    }

    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @return Result 前端提示信息
     */
    @GetMapping("/online")
    @OperLog(operationModel = "用户管理", operationDesc = "获取在线用户", operationType = OperationConst.SELECT)
    public Result online() {
        List<Object> users = new ArrayList<Object>();
        // 查询所有Redis键
        Set<String> keys = JedisUtil.keysS(Constant.PREFIX_SHIRO_REFRESH_TOKEN + "*");
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 根据:分割key，获取最后一个字符(帐号)
                String[] strArray = key.split(":");
                String code = strArray[strArray.length - 1];
                User user = userService.getUser(code);
                // 设置登录时间
                user.setLoginTime(new Date(Long.parseLong(JedisUtil.getObject(key).toString())));
                users.add(user);
            }
        }
        if (CollectionUtils.isEmpty(users)) {
            throw new BusinessException("查询失败(Query Failure)");
        }
        return Result.success(users);
    }
}
