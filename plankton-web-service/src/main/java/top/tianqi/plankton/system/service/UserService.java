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
     * 获取所有用户信息
     *
     * @param request
     * @return
     */
    Page<User> listUserPage(Page<User> request);

    /**
     * 根据ieml获取user
     * @param imel 会员编号
     * @return
     */
    User getUser(String imel);

    Page<User> getPage(String ieml, String username, Page<User> page);
}
