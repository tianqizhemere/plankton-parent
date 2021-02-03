package top.tianqi.plankton.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.User;

import java.util.List;

/**
 * 用户数据表 Mapper 接口
 * @author Wukh
 * @create 2021-01-04
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据code获取user
     * @param code 用户唯一标识
     * @return
     */
    User findUser(@Param("code") String code);

    /**
     * 加载数据列表
     * @param code 会员编号
     * @param username 用户名
     * @param page 分页对象
     * @return Page<User> 用户分页对象
     */
    List<User> findPage(@Param("page") Page<User> page, @Param("code") String code, @Param("username") String username);
}
