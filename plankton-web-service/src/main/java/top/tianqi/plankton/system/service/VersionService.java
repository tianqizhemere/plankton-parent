package top.tianqi.plankton.system.service;

import top.tianqi.plankton.base.service.BaseService;
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
}
