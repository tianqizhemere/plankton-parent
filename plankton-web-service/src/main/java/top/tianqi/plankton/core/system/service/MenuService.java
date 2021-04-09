package top.tianqi.plankton.core.system.service;

import top.tianqi.plankton.core.common.service.BaseService;
import top.tianqi.plankton.core.system.entity.Menu;

import java.util.List;

/**
 * 菜单服务层接口
 *
 * @author Wukh
 * @create 2021-01-23
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 获取树形菜单数据列表
     *
     * @param username 当前登录用户
     * @return List<Menu> 菜单列表
     */
    List<Menu> findNavTree(String username);
}
