package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.system.entity.VersionInfo;

import java.util.List;

/**
 * 版本持久层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionMapper extends BaseMapper<VersionInfo> {

    /**
     * 加载数据列表
     * @param name
     * @param page
     * @return
     */
    List<VersionInfo> findList(@Param("name") String name, @Param("page") Page<BaseEntity> page);

}
