package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Role;

import java.util.List;

/**
 * 角色持久层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findPage(@Param("name") String name, @Param("page") Page<Role> page);
}
