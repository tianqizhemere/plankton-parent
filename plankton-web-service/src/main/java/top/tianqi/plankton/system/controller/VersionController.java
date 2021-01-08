package top.tianqi.plankton.system.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.system.entity.Mobile;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
import javax.validation.Valid;

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
     * @param mobile 手机信息
     * @return
     */
    @GetMapping(value = "/checkVersion")
    public Result checkVersion(@Valid Mobile mobile, BindingResult result){
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        VersionInfo versionInfo = versionService.checkVersion(mobile);
        return SUCCESS_MESSAGE(versionInfo);
    }
}
