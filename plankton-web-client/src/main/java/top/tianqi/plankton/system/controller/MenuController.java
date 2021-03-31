package top.tianqi.plankton.system.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.annotation.aop.OperateLog;
import top.tianqi.plankton.common.constant.OperationConst;
import top.tianqi.plankton.common.controller.BaseController;
import top.tianqi.plankton.common.status.ErrorStateEnum;
import top.tianqi.plankton.system.entity.Menu;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.MenuService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 菜单controller
 * @author Wukh
 * @create 2021-01-23
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    @Resource(name = "menuServiceImpl")
    private MenuService menuService;

    /**
     * 获取树形菜单数据列表
     * @param username 当前登录用户
     * @return Result 前端提示信息
     */
    @GetMapping("getNavTree")
    public Result getNavTree(String username){
        if (username == null) {
            User currentUser = menuService.getCurrentUser();
            username = currentUser.getCode();
        }
        List<Menu> list = menuService.findNavTree(username);
        return SUCCESS_MESSAGE(list);
    }

    /**
     * 新增菜单
     * @param menu 菜单对象
     * @param result 后台验证对象
     * @return Result 前端提示信息
     */
    @OperateLog(model = OperationConst.MENU_MODEL, desc = "新增菜单", type = OperationConst.INSERT)
    @PostMapping("save")
    public Result save(@Valid @RequestBody Menu menu, BindingResult result){
        if (result.hasErrors()){
            return Result.error(ErrorStateEnum.MISSING_PARAMETER.getCode(), result.getFieldError().getDefaultMessage());
        }
        return SUCCESS_MESSAGE();
    }
}
