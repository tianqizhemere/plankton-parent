package top.tianqi.plankton.web.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.annotation.aop.Limit;
import top.tianqi.plankton.core.common.annotation.aop.OperateLog;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.core.common.constant.OperationConst;
import top.tianqi.plankton.core.common.enumeration.LimitTypeEnum;
import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;
import top.tianqi.plankton.core.system.entity.VersionInfo;
import top.tianqi.plankton.web.system.service.DictionariesService;
import top.tianqi.plankton.web.system.service.VersionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 版本controller
 * @author Wukh
 * @create 2021-01-08
 */
@RestController
@RequestMapping("/system/version")
public class VersionController extends BaseController {

    @Resource(name = "versionServiceImpl")
    private VersionService versionService;

    @Resource(name = "dictionariesServiceImpl")
    private DictionariesService dictionariesService;

    /**
     * 加载数据列表
     * @param name 版本编号
     * @param dictId 数据字典id
     * @return Result 前端提示信息
     */
    @RequiresPermissions(value = "system:version:view")
    @GetMapping("list")
    public Result list(String name, String dictId){
        List<String> modelNames = dictionariesService.findNameById(dictId);
        Page<VersionInfo> page = versionService.getPage(name, modelNames, getPage());
        return Result.success(page);
    }

    /**
     * 检测app版本
     * @param model 设备型号
     * @param version 当前版本
     * @return Result 前端提示信息
     */
    @Limit(count = 5, period = 60, limitType = LimitTypeEnum.IP, key = "checkVersion", prefix = "version_limit")
    @OperateLog(model = OperationConst.VERSION_MODEL, desc = "检查是否有更新版本", type = OperationConst.SELECT)
    @GetMapping("checkVersion")
    public Result checkVersion(String version, String model) {
        VersionInfo versionInfo = versionService.checkVersion(version, model);
        return SUCCESS_MESSAGE(versionInfo);
    }

    /**
     * 新增版本
     * @param versionInfo 版本对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.VERSION_MODEL, desc = "新增应用版本", type = OperationConst.INSERT)
    @RequiresPermissions("system:version:save")
    @PostMapping("save")
    public Result save(@Valid @RequestBody VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        if (versionInfo.getModel() != null) {
            for (String model : versionInfo.getModel().split(",")) {
                // 验证版本是否已存在
                QueryWrapper<VersionInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(VersionInfo::getModel, model);
                queryWrapper.lambda().eq(VersionInfo::getVersionCode, versionInfo.getVersionCode());
                int existResult = versionService.count(queryWrapper);
                if (existResult != 0) {
                    return Result.error(ErrorStateEnum.VERSION_CODE_EXIST.getCode(), ErrorStateEnum.VERSION_CODE_EXIST.getMsg());
                }
            }
        }
        versionService.save(versionInfo);
        return SUCCESS_MESSAGE();
    }

    /**
     * 修改版本
     * @param versionInfo 版本对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.VERSION_MODEL, desc = "修改应用版本", type = OperationConst.UPDATE)
    @RequiresPermissions("system:version:update")
    @PostMapping("update")
    public Result update(@Valid @RequestBody VersionInfo versionInfo, BindingResult result){
        if (result.hasErrors()) {
            return new Result(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        versionService.updateById(versionInfo);
        return SUCCESS_MESSAGE();
    }

    /**
     * 删除应用版本
     * @param versionInfos 版本对象集合
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.VERSION_MODEL, desc = "删除应用版本", type = OperationConst.DELETE)
    @RequiresPermissions(value = "system:version:delete")
    @PostMapping("delete")
    public Result delete(@RequestBody List<VersionInfo> versionInfos){
        List<Long> ids = versionInfos.stream().map(VersionInfo::getId).collect(Collectors.toList());
        versionService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }

    /**
     * 根据手机型号和型号获取当前版本更新信息
     * @param model 手机型号
     * @param version 当前版本
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.VERSION_MODEL, desc = "获取当前版本信息", type = OperationConst.SELECT)
    @GetMapping("info")
    public Result info(String model, String version){
        VersionInfo versionInfo = versionService.getVersionInfo(version, model);
        return SUCCESS_MESSAGE(versionInfo);
    }
}
