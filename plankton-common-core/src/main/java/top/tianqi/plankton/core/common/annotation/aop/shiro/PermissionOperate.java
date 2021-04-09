package top.tianqi.plankton.core.common.annotation.aop.shiro;

import top.tianqi.plankton.core.common.enumeration.RelationTypeEnum;

import java.lang.annotation.*;

/**
 * 资源
 * @author Wukh
 * @create 2021-01-10
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionOperate {

    /**
     * 资源
     * @return
     */
    String[] value() default "";

    /**
     * 关系
     * @return
     */
    RelationTypeEnum relation() default RelationTypeEnum.AND;
}
