package top.tianqi.plankton.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.mapper.VersionDao;
import top.tianqi.plankton.system.service.VersionService;

/**
 * 版本检测服务层实现
 * @author Wukh
 * @create 2021-01-08
 */
@Service(value = "versionServiceImpl")
public class VersionServiceImpl extends BaseServiceImpl<VersionDao, VersionInfo> implements VersionService {

    @Autowired
    private VersionDao versionDao;

    @Override
    public VersionInfo checkVersion(String currentVersion) {

        return null;
    }
}
