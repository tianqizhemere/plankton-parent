package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Menu;
import top.tianqi.plankton.system.entity.RoleMenu;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.entity.UserRole;
import top.tianqi.plankton.system.mapper.MenuMapper;
import top.tianqi.plankton.system.mapper.RoleMenuMapper;
import top.tianqi.plankton.system.mapper.UserRoleMapper;
import top.tianqi.plankton.system.service.MenuService;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务层实现
 * @author Wukh
 * @create 2021-01-23
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> findNavTree(String username) {
        User user = userService.getUser(username);

        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(UserRole::getUserId, user.getId());
        // 查询用户角色id
        List<Long> roleIds = userRoleMapper.selectList(userRoleQueryWrapper)
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 查询角色对应的菜单id
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.lambda().in(RoleMenu::getRoleId, roleIds);

        List<Long> menusIds = roleMenuMapper.selectList(roleMenuQueryWrapper)
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Menu> menus = menuMapper.selectBatchIds(menusIds);
        handleMenus(menus);
        return menus;
    }

    /**
     * 调整菜单结构
     * @param menus 菜单列表
     */
    private void handleMenus(List<Menu> menus) {
        menus.forEach(m -> {
            List<Menu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });
        menus.removeIf(m -> m.getParentId() != 0);
    }

    /**
     * 根据父节点查询所有子节点菜单
     * @param parentId 父节点
     * @return List<Menu> 子菜单列表
     */
    public List<Menu> getAllByParentId(Long parentId) {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Menu::getParentId, parentId);
        return menuMapper.selectList(lambdaQueryWrapper);
    }
}
