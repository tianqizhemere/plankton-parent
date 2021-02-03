package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.system.entity.User;

import java.util.Set;

/**
 * 用户服务层接口
 * @author Wukh
 * @create 2021-01-04
 */
public interface UserService extends BaseService<User> {

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    Boolean userLogin(String userName, String password);

    /**
     * 获取所有用户信息
     *
     * @param request
     * @return
     */
    Page<User> listUserPage(Page<User> request);

    /**
     * 根据code获取user
     * @param code 用户唯一标识
     * @return
     */
    User getUser(String code);

    /**
     * 分页查询
     * @param code
     * @param username
     * @param page
     * @return
     */
    Page<User> getPage(String code, String username, Page<User> page);

    /**
     * 通过用户名获取用户角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Set<String> getUserRoles(Long userId);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param userId 用户id
     * @return 权限集合
     */
    Set<String> getUserPermissions(Long userId);
}
