package top.tianqi.plankton.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.Limit;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.enumeration.LimitTypeEnum;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 版本controller
 * @author Wukh
 * @create 2021-01-08
 */
@RestController
@RequestMapping(value = "/system/version")
public class VersionController extends BaseController {

    @Resource(name = "versionServiceImpl")
    private VersionService versionService;

    /**
     * 加载数据列表
     * @param name 版本编号
     * @param dictId 数据字典id
     * @return Result 前端提示信息
     */
    @OperLog(model = "版本管理", desc = "应用版本列表", type = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(String name, String dictId){
        PageResult page = versionService.getPage(name, dictId, getPage());
        return Result.success(page);
    }

    /**
     * 检测app版本
     * @param model 设备型号
     * @param version 当前版本
     * @return Result 前端提示信息
     */
    //@RequiresPermissions("system:version:check")
    @Limit(count = 5, period = 60, limitType = LimitTypeEnum.IP, key = "checkVersion", prefix = "limit")
    @OperLog(model = "版本管理", desc = "检测应用版本", type = OperationConst.SELECT)
    @GetMapping(value = "/checkVersion")
    public Result checkVersion(String version, String model) throws Exception {
        VersionInfo versionInfo = versionService.checkVersion(version, model);
        return SUCCESS_MESSAGE(versionInfo);
    }

    @OperLog(model = "版本管理", desc = "新增应用版本", type = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        versionService.insert(versionInfo);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "版本管理", desc = "修改应用版本", type = OperationConst.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        versionService.updateById(versionInfo);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "版本管理", desc = "删除应用版本", type = OperationConst.DELETE)
    @PostMapping(value = "/delete")
    @RequiresPermissions(value = "system:version:delete")
    public Result delete(@RequestBody List<VersionInfo> versionInfos){
        for (VersionInfo versionInfo : versionInfos) {
            versionService.deleteById(versionInfo.getId());
        }
        return SUCCESS_MESSAGE();
    }

    /**
     * 根据手机型号查询最新版本更新信息
     * @param model 手机型号
     * @param version 当前版本
     * @return 前端提示信息
     */
    @OperLog(model = "版本管理", desc = "查询最新版本信息", type = OperationConst.SELECT)
    @GetMapping(value = "/info")
    public Result info(String model, String version){
        VersionInfo versionInfo = versionService.getVersionInfo(version, model);
        return SUCCESS_MESSAGE(versionInfo);
    }
}
