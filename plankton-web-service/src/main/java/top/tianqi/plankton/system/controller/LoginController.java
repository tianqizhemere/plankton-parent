package top.tianqi.plankton.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.UnauthorizedException;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 登录controller
 * @author Wukh
 * @create 2021-01-14
 */
@RestController
public class LoginController extends BaseController {

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    /**
     * 登录
     * @param ieml 会员编号
     * @return Result
     */
    @OperLog(operationModel = "用户管理", operationDesc = "用户登录", operationType = OperationConst.SELECT)
    @PostMapping(value = "/login")
    public Result login(String ieml, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        User user = userService.getUser(ieml);
        if (user == null) {
            throw new UnauthorizedException("登录失败，ieml不存在或错误");
        }
        // 获取当前用户主体
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + ieml)) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + ieml);
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + ieml, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(ieml, currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.success("登录成功(Login Success.)", token);
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
