package top.tianqi.plankton.system.service;

import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.VersionInfo;

/**
 * 版本检测服务层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionService extends BaseService<VersionInfo> {

    VersionInfo checkVersion(String currentVersion);
}
