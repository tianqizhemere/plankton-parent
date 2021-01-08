package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.base.BaseController;
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
}
