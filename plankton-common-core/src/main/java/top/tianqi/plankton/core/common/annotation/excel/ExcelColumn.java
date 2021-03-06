package top.tianqi.plankton.core.common.annotation.excel;

import java.lang.annotation.*;

/**
 * 自定义实体类所需要的bean(Excel属性标题、位置等)
 * @author Wukh
 * @create 2021-05-06
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    /** 定义字段在excel的单元格列坐标位置 */
    int order() default 9999;

    /** 定义列坐标对应的标题 */
    String title() default "";

    /** 定义列宽 */
    int column() default 100;

    /** 定义日期显示格式 */
    String pattern() default "yyyy-MM-dd HH:mm:ss";
}
