package top.tianqi.plankton.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
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
        Date date = new Date();
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
        this.strictInsertFill(metaObject, MODIFY_TIME, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, MODIFY_TIME, Date.class, new Date());
    }
}
