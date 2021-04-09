package top.tianqi.plankton.config;

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

    private static final Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    /** 创建时间 */
    public static final String CREATE_TIME = "createTime";

    /** 修改时间 */
    public static final String MODIFY_TIME = "modifyTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("start install fill ......");
        Date date = new Date();
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
        this.strictInsertFill(metaObject, MODIFY_TIME, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("start update fill ......");
        this.strictInsertFill(metaObject, MODIFY_TIME, Date.class, new Date());
    }
}
