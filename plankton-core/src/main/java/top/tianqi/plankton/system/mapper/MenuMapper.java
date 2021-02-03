package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Menu;

import java.util.List;

/**
 * 菜单持久层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据父节点查询所有子节点菜单
     * @param parentId 父节点
     * @return List<Menu> 子菜单列表
     */
    List<Menu> findAllByParentId(@Param("parentId") Long parentId);
}
