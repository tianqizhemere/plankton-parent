package top.tianqi.plankton.system.service;

import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.Menu;

import java.util.List;

/**
 * 菜单服务层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 获取树形菜单数据列表
     * @param username 当前登录用户
     * @return List<Menu> 菜单列表
     */
    List<Menu> findNavTree(String username);

    /**
     * 根据菜单名称查询数据列表
     * @param menuType 菜单类型
     * @param name 菜单名称
     * @return
     */
    List<Menu> findMenuTree(int menuType, String name);
}
