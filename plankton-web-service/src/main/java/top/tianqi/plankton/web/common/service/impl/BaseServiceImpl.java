package top.tianqi.plankton.web.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import top.tianqi.plankton.web.common.service.BaseService;
import top.tianqi.plankton.core.common.constant.Constant;
import top.tianqi.plankton.core.common.exception.BusinessException;
import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;
import top.tianqi.plankton.core.system.entity.User;
import top.tianqi.plankton.web.system.service.UserService;
import top.tianqi.plankton.util.JwtUtil;

import javax.annotation.Resource;

/**
 * service层超类
 * @author Wukh
 * @create 2021-01-04
 */
@Transactional
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Override
    public User getCurrentUser() {
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            String token = SecurityUtils.getSubject().getPrincipal().toString();
            // 解密获得code
            String code = JwtUtil.getClaim(token, Constant.ACCOUNT);
            User user = userService.getUser(code);
            if (user == null) {
                throw new BusinessException(ErrorStateEnum.TOKEN_INVALID);
            }
            return user;
        }
        return null;
    }
}
