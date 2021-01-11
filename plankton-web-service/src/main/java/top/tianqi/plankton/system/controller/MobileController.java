package top.tianqi.plankton.system.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.BaseController;
import top.tianqi.plankton.system.entity.Mobile;
import top.tianqi.plankton.system.service.MobileService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 手机型号controller
 * @author Wukh
 * @create 2021-01-10
 */
@RestController
@RequestMapping(value = "/system/mobile")
public class MobileController extends BaseController {

    @Resource(name = "mobileServiceImpl")
    private MobileService mobileService;

    /**
     * 新增手机信息
     * @param mobile 手机信息对象
     * @param result 后台验证
     * @return 前端提示信息
     */
    @PostMapping(value = "/save")
    public Result save(@Valid Mobile mobile, BindingResult result) {
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        mobileService.insert(mobile);
        return SUCCESS_MESSAGE();
    }

    /**
     * 修改手机信息
     * @param mobile 手机信息对象
     * @param result 后台验证
     * @return 前端提示信息
     */
    @PostMapping(value = "/update")
    public Result update(@Valid Mobile mobile, BindingResult result) {
        if (result.hasErrors()) {
            return new Result(500, result.getFieldError().getDefaultMessage());
        }
        mobileService.updateById(mobile);
        return SUCCESS_MESSAGE();
    }
}
