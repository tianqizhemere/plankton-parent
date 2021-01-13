package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.Auth;

import java.util.List;

/**
 * 权限持久层
 * @author Wukh
 * @create 2021-01-08
 */
public interface AuthDao extends BaseMapper<Auth> {

    /**
     * 根据用户 id 获取权限
     * @param userId 用户id
     * @return List<Auth> 权限列表
     */
    List<Auth> findListByUserId(@Param("userId") Long userId);
}
