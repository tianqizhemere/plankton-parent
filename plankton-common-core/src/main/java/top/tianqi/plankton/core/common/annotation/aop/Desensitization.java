package top.tianqi.plankton.core.common.annotation.aop;

import top.tianqi.plankton.core.common.enumeration.DesensitizationTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解
 * @author Wukh
 * @create 2021-03-24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {

    /**
     * 脱敏规则类型
     */
    DesensitizationTypeEnum type();

    /**
     * 附加值, 自定义正则表达式等
     */
    String[] attach() default "";
}
