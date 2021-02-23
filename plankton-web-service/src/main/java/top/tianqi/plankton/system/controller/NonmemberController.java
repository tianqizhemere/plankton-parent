package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.system.service.NonmemberService;

import javax.annotation.Resource;

/**
 * 非会员controller
 * @author Wukh
 * @create 2021-01-19
 */
@RestController
@RequestMapping(value = "/system/nonmember")
public class NonmemberController extends BaseController {

    @Resource(name = "nonmemberServiceImpl")
    private NonmemberService nonmemberService;

    /**
     * 加载数据列表
     * @return Result 前端提示信息
     */
    @GetMapping(value = "/page")
    public Result findPage(){
        return SUCCESS_MESSAGE();
    }
}
