package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
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
     * @param queryWrapper 查询条件封装
     * @return List<RoleMenu>
     */
    List<RoleMenu> findListByRoleIds(@Param(Constants.WRAPPER) QueryWrapper<RoleMenu> queryWrapper);
}
