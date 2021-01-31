package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.service.RoleService;

import javax.annotation.Resource;

/**
 * 角色controller
 * @author Wukh
 * @create 2021-01-24
 */
@RestController
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController {

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param name 角色名称
     * @return Result 前端提示信息
     */
    @OperLog(model = "角色管理", desc = "分页查询", type = OperationConst.SELECT)
    @GetMapping(value = "/findPage")
    public Result findPage(String name){
        PageResult page = roleService.findPage(name, getPage());
        return SUCCESS_MESSAGE(page);
    }
}
