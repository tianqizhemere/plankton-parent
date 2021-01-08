package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.system.entity.Auth;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.AuthService;

import java.util.List;

/**
 * 用户权限服务层实现
 * @author Wukh
 * @create 2021-01-04
 */
@Service(value = "authServiceImpl")
public class AuthServiceImpl implements AuthService {
    @Override
    public List<Auth> getUserAuthListById(Long id) {
        return null;
    }

    @Override
    public Boolean userLogin(String userName, String password) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public Page<User> listUserPage(Page<User> request) {
        return null;
    }
}