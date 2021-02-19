package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Attach;

import java.util.List;

/**
 * 文件持久层接口
 * @author Wukh
 * @create 2021-01-12
 */
public interface AttachMapper extends BaseMapper<Attach> {

    /**
     * 获取文件列表
     * @param recordId 标识关联记录号
     * @param dataType 文件数据类型
     * @return List<Attach> 文件列表
     */
    List<Attach> findList(@Param("recordId") Long recordId,@Param("dataType") String dataType);
}
