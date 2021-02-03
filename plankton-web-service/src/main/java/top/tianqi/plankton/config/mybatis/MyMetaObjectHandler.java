package top.tianqi.plankton.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Wukh
 * @create 2021-02-03
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        Date date = new Date();
        this.strictInsertFill(metaObject, "createTime", Date.class, date); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "modifyTime", Date.class, date); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "modifyTime", Date.class, new Date()); // 起始版本 3.3.0(推荐使用)
    }
}
