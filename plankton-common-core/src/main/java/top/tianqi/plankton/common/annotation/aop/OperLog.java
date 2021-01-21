package top.tianqi.plankton.common.annotation.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 * @author tianQi
 * @create 2021-01-07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
    /** 操作模块 */
    String model() default "";
    /** 操作类型 */
    String type() default "";
    /** 操作说明 */
    String desc() default "";

}
