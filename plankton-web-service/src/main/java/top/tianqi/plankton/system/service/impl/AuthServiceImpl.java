package top.tianqi.plankton.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tianqi.plankton.common.service.impl.BaseServiceImpl;
import top.tianqi.plankton.system.entity.Auth;
import top.tianqi.plankton.system.mapper.AuthMapper;
import top.tianqi.plankton.system.service.AuthService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限服务层实现
 *
 * @author Wukh
 * @create 2021-01-04
 */
@Service
public class AuthServiceImpl extends BaseServiceImpl<AuthMapper, Auth> implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public Set<String> getUserAuthListById(Long userId) {
        QueryWrapper<Auth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ur.user_id", userId);
        List<Auth> list = authMapper.findListByUserId(queryWrapper);
        Set<String> auths = new HashSet<>();
        for (Auth auth : list) {
            auths.add(auth.getCode());
        }
        return auths;
    }
}
