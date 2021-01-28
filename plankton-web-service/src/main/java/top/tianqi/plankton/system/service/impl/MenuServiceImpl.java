package top.tianqi.plankton.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Menu;
import top.tianqi.plankton.system.entity.RoleMenu;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.entity.UserRole;
import top.tianqi.plankton.system.mapper.*;
import top.tianqi.plankton.system.service.MenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务层实现
 * @author Wukh
 * @create 2021-01-23
 */
@Service(value = "menuServiceImpl")
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> findNavTree(String username) {
        User user = userMapper.findUser(username);
        // 查询用户角色id
        List<Long> roleIds = userRoleMapper.findListByUserId(user.getId())
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 查询角色对应的菜单id
        List<Long> menusIds = roleMenuMapper.findListByRoleIds(roleIds)
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
        return menuMapper.findAllByParentId(parentId);
    }
}