package top.tianqi.plankton.system.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.AuthService;
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

    @Resource(name = "authServiceImpl")
    private AuthService authService;

    /**
     * 加载用户列表
     * @return Result 前端提示信息
     */
    //@RequiresPermissions("system:user:index")
    @OperLog(model = "用户管理", desc = "查询用户列表", type = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(String code, String username){
        PageResult page = userService.getPage(code, username, getPage());
        return SUCCESS_MESSAGE(page);
    }


    //@RequiresPermissions("system:user:save")
    @OperLog(model = "用户管理", desc = "新增用户", type = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        User baseUser = userService.getUser(user.getCode());
        if (baseUser != null) {
            return Result.error(1007, "code已存在");
        }
        userService.insert(user);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "用户管理", desc = "修改用户", type = OperationConst.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        userService.updateById(user);
        return SUCCESS_MESSAGE();
    }

    /**
     * 根据username获取用户信息
     * @param username 用户名(code)
     * @return Result 前端提示信息
     */
    @GetMapping(value = "/findByName")
    public Result findByName(String username){
        User user = userService.getUser(username);
        // 获取登录时间
        user.setLoginTime(new Date(Long.parseLong(JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username).toString())));
        return SUCCESS_MESSAGE(user);
    }

    /**
     * 查找用户的菜单权限标识集合
     * @param username 用户名(code)
     * @return Result 前端提示信息
     */
    @GetMapping(value = "/findPermissions")
    public Result findPermissions(String username){
        User user = userService.getUser(username);
        Set<String> permissions = authService.getUserAuthListById(user.getId());
        return SUCCESS_MESSAGE(permissions);
    }

    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @return Result 前端提示信息
     */
    @GetMapping("/online")
    @OperLog(model = "用户管理", desc = "获取在线用户", type = OperationConst.SELECT)
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
