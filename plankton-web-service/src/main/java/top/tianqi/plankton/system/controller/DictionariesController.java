package top.tianqi.plankton.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperateLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.Dictionary;
import top.tianqi.plankton.system.service.DictionariesService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 获取树形字典
     * @return Result 前端提示信息
     */
    @RequiresPermissions(value = "system:dict:view")
    @GetMapping(value = "/findTree")
    public Result findTree(){
        List<Dictionary> page = dictionariesService.findTree();
        return SUCCESS_MESSAGE(page);
    }

    /**
     * 新增字典
     * @param dictionaries 字典对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.DICTIONARY_MODEL, desc = "新增字典", type = OperationConst.INSERT)
    @RequiresPermissions(value = "system:dict:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody Dictionary dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.save(dictionaries);
        return SUCCESS_MESSAGE();
    }

    /**
     * 修改字典
     * @param dictionaries 字典对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.DICTIONARY_MODEL, desc = "修改字典", type = OperationConst.UPDATE)
    @RequiresPermissions(value = "system:dict:update")
    @PostMapping(value = "/update")
    public Result update(@Valid @RequestBody Dictionary dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.updateById(dictionaries);
        return SUCCESS_MESSAGE();
    }

    /**
     * 删除字典
     * @param dictionaries 字典集合
     * @return Result
     */
    @OperateLog(model = OperationConst.DICTIONARY_MODEL, desc = "删除字典", type = OperationConst.DELETE)
    @RequiresPermissions(value = "system:dict:delete")
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody List<Dictionary> dictionaries) {
        List<Long> ids = dictionaries.stream().map(Dictionary::getId).collect(Collectors.toList());
        dictionariesService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }
}
