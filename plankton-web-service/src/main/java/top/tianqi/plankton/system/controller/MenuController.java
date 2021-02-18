package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperLog;
import top.tianqi.plankton.common.base.controller.BaseController;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.system.entity.Menu;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.MenuService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单controller
 * @author Wukh
 * @create 2021-01-23
 */
@RestController
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

    @Resource(name = "menuServiceImpl")
    private MenuService menuService;

    /**
     * 获取树形菜单数据列表
     * @param username 当前登录用户
     * @return Result 前端提示信息
     */
    @OperLog(model = "菜单管理", desc = "树形菜单", type = OperationConst.SELECT)
    @GetMapping(value = "/getNavTree")
    public Result getNavTree(String username){
        if (username == null) {
            User currentUser = menuService.getCurrentUser();
            username = currentUser.getCode();
        }
        List<Menu> list = menuService.findNavTree(username);
        return SUCCESS_MESSAGE(list);
    }

    /**
     * 根据菜单名称查询数据列表
     * @param name 菜单名称
     * @return
     */
    @GetMapping("/findMenuTree")
    public Result findMenuTree(String name){
        List<Menu> list = menuService.findMenuTree(0, name);
        return Result.success(list);
    }

}
