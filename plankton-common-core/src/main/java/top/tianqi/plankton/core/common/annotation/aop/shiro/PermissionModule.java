package top.tianqi.plankton.core.common.annotation.aop.shiro;

import java.lang.annotation.*;

/**
 * 资源模块
 * @author Wukh
 * @create 2021-01-10
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionModule {
    String value() default "";
}
