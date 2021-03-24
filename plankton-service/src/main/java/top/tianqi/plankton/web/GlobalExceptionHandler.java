package top.tianqi.plankton.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.exception.LimitException;
import top.tianqi.plankton.common.exception.RedisConnectException;
import top.tianqi.plankton.common.exception.UnauthorizedException;
import top.tianqi.plankton.common.status.ErrorStateEnum;
import top.tianqi.plankton.system.entity.User;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Set;

/**
 * 全局异常统一处理
 * @author Wukh
 * @create 2021-01-04
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return Result.error(HttpStatus.BAD_REQUEST.value(), message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        logger.error(message.toString(), e);
        return  Result.error(HttpStatus.BAD_REQUEST.value(),message.toString());
    }

    /**
     * 上传文件大小超出限制
     * @param e MaxUploadSizeExceededException
     * @return Result
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String message = "文件大小超出限制";
        logger.error(message, e.getMessage());
        return Result.error(HttpStatus.PAYLOAD_TOO_LARGE.value(), message);
    }
}
