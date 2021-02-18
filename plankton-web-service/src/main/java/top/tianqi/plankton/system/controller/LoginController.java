package top.tianqi.plankton.system.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.Nonmember;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.NonmemberService;
import top.tianqi.plankton.system.service.UserService;
import top.tianqi.plankton.system.vo.UserVO;

import javax.annotation.Resource;
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

    /**
     * 登录
     * @param loginUser 登录对象
     * @return Result 前端提示信息
     */
    @OperLog(model = "登录管理", desc = "登录", type = OperationConst.LOGIN)
    @PostMapping(value = "/login")
    public Result login(@RequestBody User loginUser)  {
        if (loginUser == null) {
            throw new BusinessException("登录参数不能为空");
        }
        User user = userService.getUser(loginUser.getCode());
        if (user == null) {
            Nonmember nonmember = new Nonmember();
            nonmember.setCreateTime(new Date());
            nonmember.setModifyTime(new Date());
            nonmember.setModel(loginUser.getModel());
            nonmember.setCode(loginUser.getCode());
            nonmemberService.save(nonmember);
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
        // 设置RefreshToken，时间戳为当前时间戳，覆盖已有的RefreshToken
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getCode(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        String token = JwtUtil.sign(user.getCode(), currentTimeMillis);
        userVO.setAuthorization(token);
        user.setLoginTime(new Date());
        userService.updateById(user);
        return Result.success("登录成功(Login Success.)", userVO);
    }

    @OperLog(model = "登录管理", desc = "退出", type = OperationConst.LOGIN)
    @GetMapping(value = "/logout")
    public Result logout() {
        return SUCCESS_MESSAGE("登出成功");
    }
}
