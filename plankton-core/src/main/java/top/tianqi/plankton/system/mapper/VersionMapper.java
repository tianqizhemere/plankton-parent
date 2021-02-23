package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.VersionInfo;

import java.util.List;

/**
 * 版本持久层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionMapper extends BaseMapper<VersionInfo> {

    /**
     * 检测是否有更新
     * @param checkVersionWrapper 查询条件
     * @return List<VersionInfo> 是否有更新
     */
    List<VersionInfo> checkVersion(@Param(Constants.WRAPPER) QueryWrapper<VersionInfo> checkVersionWrapper);
}
