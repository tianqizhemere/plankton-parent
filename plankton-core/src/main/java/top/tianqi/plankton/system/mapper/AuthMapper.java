package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Auth;

import java.util.List;

/**
 * 权限持久层接口
 * @author Wukh
 * @create 2021-01-08
 */
public interface AuthMapper extends BaseMapper<Auth> {

    /**
     * 根据用户 id 获取权限
     * @param queryWrapper 查询条件封装
     * @return List<Auth> 权限列表
     */
    List<Auth> findListByUserId(@Param(Constants.WRAPPER) QueryWrapper<Auth> queryWrapper);
}
