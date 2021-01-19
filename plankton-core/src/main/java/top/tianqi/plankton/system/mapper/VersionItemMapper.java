package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.tianqi.plankton.system.entity.VersionInfoItem;

/**
 * 版本持久层接口
 * @author Wukh
 * @create 2021-01-08
 */
@Mapper()
public interface VersionItemMapper extends BaseMapper<VersionInfoItem> {
}
