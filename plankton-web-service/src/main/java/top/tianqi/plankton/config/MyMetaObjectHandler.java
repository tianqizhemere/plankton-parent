package top.tianqi.plankton.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动设置公用字段属性值
 * @author Wukh
 * @create 2021-02-03
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /** 创建时间 */
    public static final String CREATE_TIME = "createTime";

    /** 修改时间 */
    public static final String MODIFY_TIME = "modifyTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(CREATE_TIME, new Date(), metaObject);
        setFieldValByName(MODIFY_TIME, new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(MODIFY_TIME, new Date(), metaObject);
    }
}
