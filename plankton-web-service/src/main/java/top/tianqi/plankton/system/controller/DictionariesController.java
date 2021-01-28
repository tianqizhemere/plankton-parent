package top.tianqi.plankton.system.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Result findTree(){
        List<Dictionaries> page = dictionariesService.findTree();
        return SUCCESS_MESSAGE(page);
    }

    @OperLog(model = "字段管理", desc = "新增字典", type = OperationConst.INSERT)
    @PostMapping(value = "/save")
    public Result save(@Valid Dictionaries dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.insert(dictionaries);
        return SUCCESS_MESSAGE();
    }

    @OperLog(model = "字段管理", desc = "修改字典", type = OperationConst.INSERT)
    @PostMapping(value = "/update")
    public Result update(@Valid Dictionaries dictionaries, BindingResult result){
        if (result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }
        dictionariesService.updateById(dictionaries);
        return SUCCESS_MESSAGE();
    }
}
