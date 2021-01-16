package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.Auth;
import top.tianqi.plankton.system.entity.User;

import java.util.Set;

/**
 * 用户权限服务层接口
 * @author Wukh
 * @create 2021-01-04
 */
public interface AuthService extends BaseService<Auth> {

    /**
     * 根据用户 id 获取权限
     * @param userId 用户id
     * @return Set<String> 权限列表
     */
    Set<String> getUserAuthListById(Long userId);

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    Boolean userLogin(String userName, String password);

    /**
     * 获取当前用户
     *
     * @return
     */
    User getCurrentUser();

    /**
     * 获取所有用户信息
     *
     * @param request
     * @return
     */
    Page<User> listUserPage(Page<User> request);
}
