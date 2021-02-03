package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.tianqi.plankton.system.entity.RoleMenu;

import java.util.List;

/**
 * 角色对应菜单持久层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色id集合查询菜单
     * @param roleIds 角色id
     * @return List<RoleMenu>
     */
    List<RoleMenu> findListByRoleIds(List<Long> roleIds);
}
