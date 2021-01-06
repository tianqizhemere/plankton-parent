package top.tianqi.plankton.common.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.tianqi.plankton.system.entity.Auth;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.AuthService;
import top.tianqi.plankton.system.service.UserService;

import java.util.List;

/**
 * 自定义 realm
 * @author Wukh
 * @create 2021-01-04
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        // 获取用户信息
        User user = null;
        // 注入凭证校验,userName 作为盐
        ByteSource salt = ByteSource.Util.bytes(user.getUserName());
        // 注入凭证匹配器.进行 md5 校验
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        return authcInfo;
    }

    /**
     * 设置角色与授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 缓存中取用户信息
        User user = (User) principals.getPrimaryPrincipal();
        // 查询权限
        List<Auth> authList = authService.getUserAuthListById(user.getId());
        // 构造权限后返回
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 构造权限
        for (Auth auth : authList) {
            authorizationInfo.addStringPermission(auth.getAuthName());
        }
        return authorizationInfo;
    }
}

