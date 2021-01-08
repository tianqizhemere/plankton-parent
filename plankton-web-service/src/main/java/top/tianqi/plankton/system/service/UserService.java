package top.tianqi.plankton.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import top.tianqi.plankton.base.service.BaseService;
import top.tianqi.plankton.system.entity.User;

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
