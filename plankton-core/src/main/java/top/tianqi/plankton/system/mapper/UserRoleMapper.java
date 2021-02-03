package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.UserRole;

import java.util.List;

/**
 * 用户-角色持久层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> findListByUserId(@Param("id") Long id);
}
