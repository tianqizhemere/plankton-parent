package top.tianqi.plankton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.exception.BusinessException;

/**
 * 全局异常统一处理
 * @author Wukh
 * @create 2021-01-04
 */
@ControllerAdvice
public class MyControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        logger.error("未知异常！原因是:",ex);
        return new Result(-1, "未知错误");
    }

    /**
     * 拦截捕捉业务异常 BusinessException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Result myErrorHandler(BusinessException ex) {
        logger.error("业务异常", ex);
        return new Result(ex.getErrorCode(), ex.getMessage());
    }
}
