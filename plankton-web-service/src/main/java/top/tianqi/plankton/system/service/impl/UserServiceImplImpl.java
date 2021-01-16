package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserDao;
import top.tianqi.plankton.system.service.UserService;

import java.util.List;

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
    public User getUser(String code) {
        return userDao.findUser(code);
    }

    @Override
    public List<User> getPage(String ieml, String username, Page<User> page) {
        List<User> page1 = userDao.findPage(page, ieml, username);
        long total = page.getTotal();
        System.out.println("total = " + total);
        return page1;
    }

    public static void main(String[] args) {
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);
    }
}
