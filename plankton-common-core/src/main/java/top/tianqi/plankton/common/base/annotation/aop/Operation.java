package top.tianqi.plankton.common.base.annotation.aop;

import java.lang.annotation.*;

/**
 * 日志记录注解
 * @author tianQi
 * @create 2021-01-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {
    String value() default "";
}
