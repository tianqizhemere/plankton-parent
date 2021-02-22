package top.tianqi.plankton.config.mybatis;

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

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictInsertFill(metaObject, "createTime", Date.class, date);
        this.strictInsertFill(metaObject, "modifyTime", Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "modifyTime", Date.class, new Date());
    }
}