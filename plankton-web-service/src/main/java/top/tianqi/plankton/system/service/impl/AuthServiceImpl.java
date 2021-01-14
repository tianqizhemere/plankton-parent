package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.base.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Auth;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.AuthDao;
import top.tianqi.plankton.system.service.AuthService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限服务层实现
 * @author Wukh
 * @create 2021-01-04
 */
@Service(value = "authServiceImpl")
public class AuthServiceImpl extends BaseServiceImpl<AuthDao, Auth> implements AuthService {

    @Autowired
    private AuthDao authDao;

    @Override
    public Set<String> getUserAuthListById(Long userId) {
        List<Auth> list = authDao.findListByUserId(userId);
        Set<String> auths = new HashSet<>();
        for (Auth auth : list) {
            auths.add(auth.getCode());
        }
        return auths;
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
