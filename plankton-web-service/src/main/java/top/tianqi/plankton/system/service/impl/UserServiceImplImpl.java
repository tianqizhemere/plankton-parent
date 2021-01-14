package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserDao;
import top.tianqi.plankton.system.service.UserService;

/**
 * 用户服务层实现
 * @author Wukh
 * @create 2021-01-04
 */
@Service(value = "userServiceImpl")
public class UserServiceImplImpl extends BaseServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

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

    @Override
    public User getUser(String imel) {
        return userDao.findUser(imel);
    }

    @Override
    public boolean insert(User user) {
        if (user.getPassword() != null) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
       return super.insert(user);
    }

    public static void main(String[] args) {
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);
    }
}
