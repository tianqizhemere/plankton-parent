package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.SuperService;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserDao;
import top.tianqi.plankton.system.service.UserService;

/**
 * @author Wukh
 * @create 2021-01-04
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl extends SuperService<UserDao, User> implements UserService {
    @Override
    public Boolean userLogin(String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        subject.login(token);
        return true;
    }

    @Override
    public Page<User> listUserPage(Page<User> request) {
        return null;
    }
}
