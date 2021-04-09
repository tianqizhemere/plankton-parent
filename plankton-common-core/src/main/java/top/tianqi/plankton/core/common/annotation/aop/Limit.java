package top.tianqi.plankton.core.common.annotation.aop;

import top.tianqi.plankton.core.common.enumeration.LimitTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流注解
 * @author Wukh
 * @create 2021-01-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /** 资源 key */
    String key() default "";

    /** key的前缀标识 */
    String prefix() default "";

    /** 时间的，单位秒 */
    int period();

    /** 限制访问次数 */
    int count();

    /** 限制类型 */
    LimitTypeEnum limitType() default LimitTypeEnum.CUSTOMER;
}
