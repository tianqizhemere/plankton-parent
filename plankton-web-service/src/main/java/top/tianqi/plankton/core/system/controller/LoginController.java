package top.tianqi.plankton.core.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.annotation.aop.Limit;
import top.tianqi.plankton.core.common.annotation.aop.OperateLog;
import top.tianqi.plankton.core.common.constant.Constant;
import top.tianqi.plankton.core.common.constant.OperationConst;
import top.tianqi.plankton.core.common.controller.BaseController;
import top.tianqi.plankton.core.common.enumeration.LimitTypeEnum;
import top.tianqi.plankton.core.common.exception.BusinessException;
import top.tianqi.plankton.core.common.status.ErrorStateEnum;
import top.tianqi.plankton.util.JedisUtil;
import top.tianqi.plankton.core.system.entity.LoginLog;
import top.tianqi.plankton.core.system.entity.Nonmember;
import top.tianqi.plankton.core.system.entity.User;
import top.tianqi.plankton.core.system.service.LoginLogService;
import top.tianqi.plankton.core.system.service.NonmemberService;
import top.tianqi.plankton.core.system.service.UserService;
import top.tianqi.plankton.util.AddressUtils;
import top.tianqi.plankton.util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 登录controller
 * @author Wukh
 * @create 2021-01-14
 */
@RestController
@PropertySource("classpath:config.properties")
public class LoginController extends BaseController {

    /** RefreshToken过期时间 */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "nonmemberServiceImpl")
    private NonmemberService nonmemberService;

    @Resource(name = "loginLogServiceImpl")
    private LoginLogService loginLogService;

    /**
     * 登录
     * @param loginUser 登录对象
     * @return Result 前端提示信息
     */
    @Limit(count = 5, period = 60 * 10, limitType = LimitTypeEnum.IP, key = "login", prefix = "login_limit")
    @OperateLog(model = OperationConst.LOGIN_MODEL, desc = "登录", type = OperationConst.LOGIN)
    @PostMapping("login")
    public Result login(@RequestBody User loginUser, HttpServletRequest request) throws IOException {
        if (loginUser == null) {
            throw new BusinessException(ErrorStateEnum.MISSING_PARAMETER);
        }
        User user = userService.getUser(loginUser.getCode());

        // 记录未注册用户
        if (user == null) {
            Nonmember nonmember = new Nonmember(loginUser.getModel(), loginUser.getCode());
            nonmemberService.save(nonmember);
            throw new BusinessException(ErrorStateEnum.USERNAME_NOT_EXIST);
        }

        if (!Objects.equals(loginUser.getModel(), user.getModel())) {
            throw new BusinessException("设备型号不一致");
        }

         // 获取当前用户主体
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + user.getCode())) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getCode());
        }

        // 设置RefreshToken，时间戳为当前时间戳，覆盖已有的RefreshToken
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getCode(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(user.getCode(), currentTimeMillis);
        user.setAuthorization(token);
        user.setLoginTime(new Date());
        user.setIp(AddressUtils.getRemoteIp(request));
        userService.updateById(user);

        // 记录登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setCode(user.getCode());
        loginLog.setIp(user.getIp());
        loginLog.setLoginTime(new Date());
        loginLogService.save(loginLog);

        return Result.success("登录成功(Login Success.)", user);
    }

    @OperateLog(model = OperationConst.LOGIN_MODEL, desc = "退出登录", type = OperationConst.LOG_OUT)
    @GetMapping("logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        return Result.success(ErrorStateEnum.REQUEST_SUCCESS);
    }
}