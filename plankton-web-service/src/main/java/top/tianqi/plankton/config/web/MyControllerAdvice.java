package top.tianqi.plankton.config.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.exception.LimitException;
import top.tianqi.plankton.common.exception.RedisConnectException;
import top.tianqi.plankton.common.exception.UnauthorizedException;
import top.tianqi.plankton.common.status.ErrorStateEnum;
import top.tianqi.plankton.system.entity.User;

/**
 * 全局异常统一处理
 * @author Wukh
 * @create 2021-01-04
 */
@RestControllerAdvice
public class MyControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex Exception
     * @return Result 前端提示信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        logger.error("未知异常！原因是:",ex);
        return Result.error(ErrorStateEnum.SYSTEM_ERROR);
    }

    /**
     * 拦截捕捉业务异常 BusinessException.class
     * @param ex BusinessException
     * @return Result 前端提示信息
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result myErrorHandler(BusinessException ex) {
        logger.error("业务异常", ex);
        return Result.error(ex.getErrorCode(), ex.getMessage());
    }

    /**
     * 拦截捕捉接口限流异常 LimitException.class
     * @param ex LimitException
     * @return Result 前端提示信息
     */
    @ExceptionHandler(value = LimitException.class)
    public Result limitErrorHandler(LimitException ex) {
        // 用户超过限流次数，暂时禁用当前用户
        User user = new User();
        user.setIsEnable(Boolean.FALSE);
        logger.error("访问过于频繁----> 触发限流");
        return Result.error(ErrorStateEnum.VISIT_FREQUENTLY, user);
    }

    /**
     * 捕捉所有Shiro异常
     * @param e ShiroException
     * @return Result 前端提示信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return Result.error(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e UnauthenticatedException
     * @return Result 前端提示信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public Result handle401(UnauthenticatedException e) {
        return Result.error(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录");
    }

    /**
     * 捕捉UnauthorizedException自定义异常
     * @return Result 前端提示信息
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handle401(UnauthorizedException e) {
        return Result.error(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 捕捉RedisConnectException redis连接异常
     * @return Result 前端提示信息
     */
    @ExceptionHandler(RedisConnectException.class)
    public Result redisConnectError(RedisConnectException e){
        return Result.error(ErrorStateEnum.REDIS_CONNECT_ERROR.getCode(), e.getMessage());
    }
}
