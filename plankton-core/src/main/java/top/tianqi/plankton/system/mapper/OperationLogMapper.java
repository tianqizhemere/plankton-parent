package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.OperationLog;

import java.util.Date;
import java.util.List;

/**
 * 用户操作日志持久层接口
 * @author tianQi
 * @create 2021-01-07
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 加载数据列表
     * @param type 操作类型
     * @param beginTime 操作时间起
     * @param endTime 操作时间止
     * @param page 分页对象
     * @return List<OperationLog> 数据列表
     */
    List<OperationLog> findPage(@Param("type") String type, @Param("name") String name,@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("page") Page<OperationLog> page);

}
