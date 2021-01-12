package top.tianqi.plankton.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.tianqi.plankton.system.entity.User;

/**
 * 用户数据表 Mapper 接口
 * @author Wukh
 * @create 2021-01-04
 */
public interface UserDao extends BaseMapper<User> {

    /**
     * 根据ieml获取用户信息
     * @param imel 会员编号
     * @return user
     */
    User findUser(@Param("imel") String imel);

}
