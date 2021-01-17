package top.tianqi.plankton.system.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;
import top.tianqi.plankton.system.vo.UserVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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
     * @param loginUser uuid值
     * @return Result
     */
    @OperLog(operationModel = "用户管理", operationDesc = "用户登录", operationType = OperationConst.SELECT)
    @PostMapping(value = "/login")
    public Result login(@RequestBody User loginUser, HttpServletResponse httpServletResponse)  {
        if (loginUser == null) {
            throw new BusinessException("登录参数不能为空");
        }
        User user = userService.getUser(loginUser.getCode());
        if (user == null) {
            throw new BusinessException("登录失败，用户不存在或错误");
        }
        if (!Objects.equals(loginUser.getModel(), user.getModel())) {
            throw new BusinessException("设备型号不一致");
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        // 获取当前用户主体
        // 清除可能存在的Shiro权限信息缓存
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + user.getCode())) {
            JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getCode());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getCode(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(user.getCode(), currentTimeMillis);
        userVO.setAuthorization(token);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return Result.success("登录成功(Login Success.)", userVO);
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
