package top.tianqi.plankton.system.service;

import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.Mobile;
import top.tianqi.plankton.system.entity.VersionInfo;

/**
 * 版本检测服务层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionService extends BaseService<VersionInfo> {

    /**
     * 检查应用版本
     * @param mobile 手机版本信息
     * @return VersionInfo更新信息
     */
    VersionInfo checkVersion(Mobile mobile) throws Exception;
}
