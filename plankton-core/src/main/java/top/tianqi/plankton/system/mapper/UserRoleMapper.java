package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.UserRole;

import java.util.List;

/**
 * 用户-角色持久层接口
 * @author Wukh
 * @create 2021-01-23
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id获取用户关联的角色id
     * @param queryWrapper 查询条件封装
     * @return List<UserRole> 用户角色id列表
     */
    List<UserRole> findListByUserId(@Param(Constants.WRAPPER) QueryWrapper<UserRole> queryWrapper);
}
