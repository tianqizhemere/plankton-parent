package top.tianqi.plankton.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.VersionInfo;
import top.tianqi.plankton.system.service.VersionService;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @OperLog(operationModel = "版本管理", operationDesc = "应用版本列表", operationType = OperationConst.SELECT)
    @GetMapping(value = "/list")
    public Result list(){
        Page<VersionInfo> page = versionService.selectPage(getPage());
        return Result.success(page);
    }

    /**
     * 检测app版本
     * @param model 设备型号
     * @param version 当前版本
     * @return Result 前端提示信息
     */
    //@RequiresPermissions("system:version:check")
    @OperLog(operationModel = "版本管理", operationDesc = "检测应用版本", operationType = OperationConst.SELECT)
    @GetMapping(value = "/checkVersion")
    public Result checkVersion(@RequestParam("version") String version, String model) throws Exception {
        VersionInfo versionInfo = versionService.checkVersion(version, model);
        return SUCCESS_MESSAGE(versionInfo);
    }

    @OperLog(operationModel = "版本管理", operationDesc = "新增应用版本", operationType = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        versionService.insert(versionInfo);
        return SUCCESS_MESSAGE();
    }

    @OperLog(operationModel = "版本管理", operationDesc = "修改应用版本", operationType = OperationConst.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Valid VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        versionService.updateById(versionInfo);
        return SUCCESS_MESSAGE();
    }

    @OperLog(operationModel = "版本管理", operationDesc = "删除应用版本", operationType = OperationConst.DELETE)
    @PostMapping(value = "/delete")
    public Result delete(Long id){
        versionService.deleteById(id);
        return SUCCESS_MESSAGE();
    }
}
