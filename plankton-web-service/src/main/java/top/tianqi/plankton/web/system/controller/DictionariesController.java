package top.tianqi.plankton.web.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.annotation.aop.OperateLog;
import top.tianqi.plankton.web.common.controller.BaseController;
import top.tianqi.plankton.core.common.constant.OperationConst;
import top.tianqi.plankton.core.system.entity.Dictionary;
import top.tianqi.plankton.web.system.service.DictionariesService;

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
@RequestMapping("/system/dictionaries")
public class DictionariesController extends BaseController {

    @Resource(name = "dictionariesServiceImpl")
    private DictionariesService dictionariesService;

    /**
     * 获取树形字典
     * @return Result 前端提示信息
     */
    @RequiresPermissions("system:dict:view")
    @GetMapping("findTree")
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
    @RequiresPermissions("system:dict:save")
    @PostMapping("save")
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
    @RequiresPermissions("system:dict:update")
    @PostMapping("update")
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
    @RequiresPermissions("system:dict:delete")
    @PostMapping("delete")
    public Result delete(@RequestBody List<Dictionary> dictionaries) {
        List<Long> ids = dictionaries.stream().map(Dictionary::getId).collect(Collectors.toList());
        dictionariesService.removeByIds(ids);
        return SUCCESS_MESSAGE();
    }
}
