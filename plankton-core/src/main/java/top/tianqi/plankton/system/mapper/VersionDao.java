package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.tianqi.plankton.system.entity.VersionInfo;

/**
 * @author Wukh
 * @create 2021-01-08
 */
@Mapper()
public interface VersionDao extends BaseMapper<VersionInfo> {
}
