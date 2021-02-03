package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
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
    List<VersionInfo> findList(@Param("name") String name, @Param("dictId") String dictId,@Param("page") Page<BaseEntity> page);

    /**
     * 检查更新
     * @param checkCode 更新的版本号
     * @param model 手机型号
     * @return List<VersionInfo> 是否有更新
     */
    List<VersionInfo> checkVersion(@Param("checkCode") String checkCode,@Param("model") String model);

    /**
     * 校验新增的版本是否已存在
     * @param model 型号
     * @param versionCode 版本编号
     * @return 是否存在 0-不存在，1-已存在
     */
    Integer checkIsExist(@Param("model") String model, @Param("versionCode") String versionCode);
}
