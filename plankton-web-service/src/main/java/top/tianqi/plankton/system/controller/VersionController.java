package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;

/**
 * 版本检测controller
 * @author Wukh
 * @create 2021-01-08
 */
@RestController
@RequestMapping(value = "/system/version")
public class VersionController extends BaseController {

    @Resource(name = "versionServiceImpl")
    private VersionService versionService;

    /**
     * 检测app版本
     * @param currentVersion 当前版本
     * @return
     */
    @GetMapping(value = "/checkVersion")
    public Result checkVersion(String currentVersion){
        VersionInfo versionInfo = versionService.checkVersion(currentVersion);
        return SUCCESS_MESSAGE();
    }
}
