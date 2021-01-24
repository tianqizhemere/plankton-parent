package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import top.tianqi.plankton.base.entity.BaseEntity;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.VersionInfo;

/**
 * 版本检测服务层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionService extends BaseService<VersionInfo> {

    /**
     * 检查应用版本
     * @param model 手机型号
     * @param currentVersion 当前版本
     * @return VersionInfo更新信息
     * @throws Exception e
     */
    VersionInfo checkVersion(String currentVersion, String model) throws Exception;

    /**
     * 根据手机型号查询最新版本更新信息
     * @param model 手机型号
     * @return versionInfo
     */
    VersionInfo getVersionInfo(String model);

    /**
     * 加载数据列表
     * @param name 版本
     * @param page 页码
     * @return PageResult page对象
     */
    PageResult getPage(String name, Page<BaseEntity> page);
}
