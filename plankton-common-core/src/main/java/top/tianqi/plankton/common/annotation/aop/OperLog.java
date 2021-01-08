package top.tianqi.plankton.common.annotation.aop;

import java.lang.annotation.*;

/**
 * 日志记录注解
 * @author tianQi
 * @create 2021-01-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperLog {
    /** 操作模块 */
    String operationModel() default "";
    /** 操作类型 */
    String operationType() default "";
    /** 操作说明 */
    String operationDesc() default "";
}
