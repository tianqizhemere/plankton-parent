package top.tianqi.plankton.common.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import top.tianqi.plankton.common.base.service.BaseService;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.exception.BusinessException;
import top.tianqi.plankton.common.status.ErrorStateEnum;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.UserService;

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
                throw new BusinessException(ErrorStateEnum.REQUEST_UNAUTHC_EXCEPTION);
            }
            return user;
        }
        return null;
    }
}
