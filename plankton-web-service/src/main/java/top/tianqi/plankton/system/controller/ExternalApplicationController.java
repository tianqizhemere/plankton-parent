package top.tianqi.plankton.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.ExternalApplication;
import top.tianqi.plankton.system.enumeration.ExternalTypeEnum;
import top.tianqi.plankton.system.service.ExternalApplicationService;
import top.tianqi.plankton.system.vo.ExternalFileVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外置应用controller
 * @author Wukh
 * @create 2021-02-01
 */
@RestController
@RequestMapping(value = "/system/externalApplication")
public class ExternalApplicationController extends BaseController {

    @Resource(name = "externalApplicationServiceImpl")
    private ExternalApplicationService externalApplicationService;

    @OperLog(model = "外置应用", desc = "分页查询", type = OperationConst.SELECT)
    @RequiresPermissions(value = "system:externalApplication:view")
    @GetMapping(value = "/page")
    public Result getPage(String name) {
        Page<ExternalApplication> page = externalApplicationService.getPage(name, getPage());
        return SUCCESS_MESSAGE(page);
    }

    @OperLog(model = "外置应用", desc = "新增外置应用", type = OperationConst.INSERT)
    @RequiresPermissions(value = "system:externalApplication:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody ExternalApplication externalApplication, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        externalApplicationService.save(externalApplication);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "外置应用", desc = "修改外置应用", type = OperationConst.UPDATE)
    @RequiresPermissions(value = "system:externalApplication:update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody ExternalApplication externalApplication, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        externalApplicationService.updateById(externalApplication);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "外置应用", desc = "删除外置应用", type = OperationConst.DELETE)
    @RequiresPermissions(value = "system:externalApplication:delete")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody List<ExternalApplication> externalApplications){
        List<Long> ids = externalApplications.stream().map(ExternalApplication::getId).collect(Collectors.toList());
        externalApplicationService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "外置应用", desc = "获取外置应用文件信息", type = OperationConst.SELECT)
    @GetMapping(value = "/fileList")
    public Result fileList(){
        List<ExternalFileVO> list = new ArrayList<>();
        for (ExternalTypeEnum externalType : ExternalTypeEnum.values()) {
            ExternalFileVO fileVO = new ExternalFileVO();
            ExternalApplication externalApplication = externalApplicationService.findByCode(externalType.getCode());
            fileVO.setCode(externalType.getCode());
            fileVO.setTypeName(externalType.getTypeName());
            if (externalApplication != null) {
                BeanUtil.copyProperties(externalApplication, fileVO, false);
            }
            list.add(fileVO);
        }
        return SUCCESS_MESSAGE(list);
    }
}