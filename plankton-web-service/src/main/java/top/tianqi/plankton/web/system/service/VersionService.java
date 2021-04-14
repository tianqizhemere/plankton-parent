package top.tianqi.plankton.web.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.VersionInfo;

import java.util.List;

/**
 * 版本检测服务层接口
 *
 * @author Wukh
 * @create 2021-01-08
 */
public interface VersionService extends BaseService<VersionInfo> {

    /**
     * 检查应用版本
     *
     * @param model          手机型号
     * @param currentVersion 当前版本
     * @return VersionInfo更新信息
     * @throws Exception e
     */
    VersionInfo checkVersion(String currentVersion, String model);

    /**
     * 根据手机型号查询最新版本更新信息
     *
     * @param version 当前版本
     * @param model   手机型号
     * @return versionInfo
     */
    VersionInfo getVersionInfo(String version, String model);

    /**
     * 加载数据列表
     *
     * @param modelNames 字典名称列表
     * @param name       版本
     * @param page       页码
     * @return PageResult page对象
     */
    Page<VersionInfo> getPage(String name, List<String> modelNames, Page<VersionInfo> page);

    /**
     * 校验新增的版本是否已存在
     *
     * @param model       型号
     * @param versionCode 版本编号
     * @return 是否存在 0-不存在，1-已存在
     */
    Integer checkIsExist(String model, String versionCode);
}
