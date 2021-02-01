package top.tianqi.plankton.system.controller;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.ExternalApplication;
import top.tianqi.plankton.system.enumeration.ExternalTypeEnum;
import top.tianqi.plankton.system.service.ExternalApplicationService;
import top.tianqi.plankton.system.vo.ExternalFileVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    @GetMapping(value = "/page")
    public Result getPage(String name) {
        PageResult page = externalApplicationService.getPage(name, getPage());
        return SUCCESS_MESSAGE(page);
    }

    @OperLog(model = "外置应用", desc = "新增外置应用", type = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody ExternalApplication externalApplication, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        externalApplicationService.insert(externalApplication);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "外置应用", desc = "修改外置应用", type = OperationConst.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody ExternalApplication externalApplication, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        externalApplicationService.updateById(externalApplication);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "外置应用", desc = "删除外置应用", type = OperationConst.DELETE)
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody List<ExternalApplication> externalApplications){
        for (ExternalApplication externalApplication : externalApplications) {
            externalApplicationService.deleteById(externalApplication.getId());
        }
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
