package top.tianqi.plankton.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.Dictionaries;
import top.tianqi.plankton.system.service.DictionariesService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 字典controller
 * @author Wukh
 * @create 2021-01-24
 */
@RestController
@RequestMapping(value = "/system/dictionaries")
public class DictionariesController extends BaseController {

    @Resource(name = "dictionariesServiceImpl")
    private DictionariesService dictionariesService;

    @GetMapping(value = "/findTree")
    @OperLog(model = "字典管理", desc = "获取字典树形", type = OperationConst.SELECT)
    @RequiresPermissions(value = "system:dict:view")
    public Result findTree(){
        List<Dictionaries> page = dictionariesService.findTree();
        return SUCCESS_MESSAGE(page);
    }

    @OperLog(model = "字典管理", desc = "新增字典", type = OperationConst.INSERT)
    @PostMapping(value = "/save")
    @RequiresPermissions(value = "system:dict:save")
    public Result save(@Valid @RequestBody Dictionaries dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.insert(dictionaries);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "字典管理", desc = "修改字典", type = OperationConst.UPDATE)
    @PostMapping(value = "/update")
    @RequiresPermissions(value = "system:dict:update")
    public Result update(@Valid @RequestBody Dictionaries dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.updateById(dictionaries);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "字典管理", desc = "删除字典", type = OperationConst.DELETE)
    @PostMapping(value = "/delete")
    @RequiresPermissions(value = "system:dict:delete")
    public Result delete(@RequestBody List<Dictionaries> dictionaries) {
        for (Dictionaries dictionary : dictionaries) {
            dictionariesService.deleteById(dictionary);
        }
        return SUCCESS_MESSAGE();
    }
}
