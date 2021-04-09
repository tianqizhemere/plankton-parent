package top.tianqi.plankton.core.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.core.common.Result;
import top.tianqi.plankton.core.common.controller.BaseController;
import top.tianqi.plankton.core.system.entity.Role;
import top.tianqi.plankton.core.system.service.RoleService;

import javax.annotation.Resource;

/**
 * 角色controller
 * @author Wukh
 * @create 2021-01-24
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param name 角色名称
     * @return Result 前端提示信息
     */
    @GetMapping("findPage")
    public Result findPage(String name){
        Page<Role> page = roleService.findPage(name, getPage());
        return SUCCESS_MESSAGE(page);
    }
}
