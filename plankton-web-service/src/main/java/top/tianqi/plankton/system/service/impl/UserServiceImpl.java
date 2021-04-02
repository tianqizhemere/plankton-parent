package top.tianqi.plankton.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.mapper.UserMapper;
import top.tianqi.plankton.system.service.AuthService;
import top.tianqi.plankton.system.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户服务层实现
 *
 * @author Wukh
 * @create 2021-01-04
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource(name = "authServiceImpl")
    private AuthService authService;

    @Override
    public User getUser(String code) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getCode, code);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public Page<User> getPage(String code, String phone, String qq, List<String> modelList, Page<User> page) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(code), User::getCode, code);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(phone), User::getPhone, phone);
        lambdaQueryWrapper.like(StringUtils.isNotBlank(qq), User::getQq, qq);
        lambdaQueryWrapper.in(!CollectionUtils.isEmpty(modelList), User::getModel, modelList);
        lambdaQueryWrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(page, lambdaQueryWrapper);
    }

    @Override
    public Set<String> getUserRoles(Long userId) {
        return null;
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        return authService.getUserAuthListById(userId);
    }

    @Override
    public IPage<User> selectByCodes(List<String> codes, Page<User> page) {
        if (CollectionUtils.isEmpty(codes)) {
            return page;
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(User::getCode, codes);
        lambdaQueryWrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(page, lambdaQueryWrapper);
    }
}
