package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Attach;

import java.util.List;

/**
 * 文件持久层接口
 * @author Wukh
 * @create 2021-01-12
 */
public interface AttachDao extends BaseMapper<Attach> {

    List<Attach> findList(@Param("recordId") Long recordId,@Param("dataType") String dataType);
}
