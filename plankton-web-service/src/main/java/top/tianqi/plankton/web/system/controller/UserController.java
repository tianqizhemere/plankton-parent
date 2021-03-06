package top.tianqi.plankton.web.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.annotation.aop.OperateLog;
import top.tianqi.plankton.core.common.constant.Constant;
import top.tianqi.plankton.core.common.constant.OperationConst;
import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;
import top.tianqi.plankton.core.system.entity.User;
import top.tianqi.plankton.util.AddressUtils;
import top.tianqi.plankton.util.JedisUtil;
import top.tianqi.plankton.util.excel.ExcelUtil;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.web.system.service.AuthService;
import top.tianqi.plankton.web.system.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户controller
 * @author tianQi
 * @create 2021-01-07
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "authServiceImpl")
    private AuthService authService;

    /**
     * 加载用户列表
     * @param code 移动设备号
     * @param phone 手机号码
     * @param qq QQ
     * @param models 设备型号
     * @return Result 前端提示信息
     */
    @RequiresPermissions("system:user:index")
    @GetMapping("list")
    public Result list(String code, String phone, String qq, String models){
        List<String> modelList = new ArrayList<>();
        if (StringUtils.isNotBlank(models)) {
            modelList = Arrays.asList(models.split(","));
        }
        Page<User> page = userService.getPage(code, phone, qq, modelList,getPage());
        return SUCCESS_MESSAGE(page);
    }

    @OperateLog(model = OperationConst.USER_MODEL, desc = "新增用户", type = OperationConst.INSERT)
    @PostMapping("save")
    public Result save(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return Result.error(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        User baseUser = userService.getUser(user.getCode());
        if (baseUser != null) {
            return Result.error(1007, "code已存在");
        }
        userService.save(user);
        return SUCCESS_MESSAGE();
    }

    @OperateLog(model = OperationConst.USER_MODEL, desc = "修改用户", type = OperationConst.UPDATE)
    @PostMapping("update")
    public Result update(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return Result.error(ErrorStateEnum.MISSING_PARAMETER.getCode(),result.getFieldError().getDefaultMessage());
        }
        userService.updateById(user);
        return SUCCESS_MESSAGE();
    }

    @OperateLog(model = OperationConst.USER_MODEL, desc = "删除用户", type = OperationConst.DELETE)
    @RequiresPermissions("system:user:delete")
    @PostMapping("delete")
    public Result delete(@RequestBody List<User> users){
        List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
        userService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }

    /**
     * 根据username获取用户信息
     * @param username 用户名(code)
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.USER_MODEL, desc = "获取用户信息", type = OperationConst.SELECT)
    @GetMapping("findByName")
    public Result findByName(@RequestParam String username){
        User user = userService.getUser(username);
        return SUCCESS_MESSAGE(user);
    }

    /**
     * 查找用户的菜单权限标识集合
     * @param username 用户名(code)
     * @return Result 前端提示信息
     */
    @GetMapping("findPermissions")
    public Result findPermissions(String username){
        User user = userService.getUser(username);
        Set<String> permissions = authService.getUserAuthListById(user.getId());
        return SUCCESS_MESSAGE(permissions);
    }

    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @param username 用户名
     * @return Result 前端提示信息
     */
    @RequiresPermissions("monitor:online:view")
    @GetMapping("online")
    public Result online(String username) throws IOException {
        List<String> codes = new ArrayList<>();
        Set<String> keys;
        if (StringUtils.isNotBlank(username)) {
            keys = JedisUtil.keysS(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username);
        } else {
            // 查询所有Redis键
            keys = JedisUtil.keysS(Constant.PREFIX_SHIRO_REFRESH_TOKEN + "*");
        }
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 根据:分割key，获取最后一个字符(帐号)
                String[] strArray = key.split(":");
                String code = strArray[strArray.length - 1];
                codes.add(code);
            }
        }
        IPage<User> page = userService.selectByCodes(codes, getPage());
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            for (User user : page.getRecords()) {
                if (user.getIp() != null) {
                    if (user.getAddress() == null) {
                        user.setAddress(AddressUtils.getIpCity(user.getIp()));
                        userService.updateById(user);
                    }
                }
            }
        }
        return Result.success(page);
    }

    /**
     * 踢出用户
     * @param users 踢出的用户列表
     * @return Result 前端提示信息
     */
    @RequiresPermissions("monitor:online:kickOut")
    @PostMapping("kickOut")
    public Result kickOut(@RequestBody List<User> users){
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                user = userService.getById(user.getId());
                JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getCode());
            }
        }
        return SUCCESS_MESSAGE();
    }

    /**
     * 导出excel
     * @return Result 前端提示信息
     */
    @GetMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response){
        Page<User> page = userService.getPage(null, null, null, null, getPage());
        List<User> records = page.getRecords();
        ExcelUtil.exportExcel(request, response, "用户数据", records, User.class);
    }
}
