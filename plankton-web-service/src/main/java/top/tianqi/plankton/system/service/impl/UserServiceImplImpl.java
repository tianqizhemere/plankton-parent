package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.common.utils.PageResult;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserMapper;
import top.tianqi.plankton.system.service.AuthService;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户服务层实现
 * @author Wukh
 * @create 2021-01-04
 */
@Service(value = "userServiceImpl")
public class UserServiceImplImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userDao;

    @Resource(name = "authServiceImpl")
    private AuthService authService;

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
    public PageResult getPage(String code, String username, Page<User> page) {
        List<User> users = userDao.findPage(page, code, username);
        return new PageResult(page.getCurrent(), page.getSize(),  page.getTotal() , page.getPages(), users);
    }

    @Override
    public Set<String> getUserRoles(Long userId) {
        return null;
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        return authService.getUserAuthListById(userId);
    }

    public static void main(String[] args) {
        String s = DigestUtils.md5Hex("123456");
        System.out.println(s);
    }
}
