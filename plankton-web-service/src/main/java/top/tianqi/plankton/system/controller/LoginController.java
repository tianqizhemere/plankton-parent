package top.tianqi.plankton.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.shiro.token.JwtToken;
import top.tianqi.plankton.common.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 登录controller
 * @author Wukh
 * @create 2021-01-14
 */
@RestController
public class LoginController extends BaseController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

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
        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String tokenStr = JwtUtil.sign(ieml);
        try {
            subject.login(new JwtToken(tokenStr));
        } catch (UnknownAccountException e) {
            return Result.error("user.account.error");
        } catch (AuthenticationException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("system.error");
        }
        return Result.success("登录成功", tokenStr);
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
