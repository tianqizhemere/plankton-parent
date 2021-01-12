package top.tianqi.plankton.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import top.tianqi.plankton.common.shiro.token.JwtUtil;
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
        String token = (String) authenticationToken.getCredentials();
        // 解密获得ieml，用于和数据库进行对比
        String imel = JwtUtil.getImel(token);
        if (imel == null) {
            throw new AuthenticationException("token 无效！");
        }
        User user = userService.getUser(imel);
        if (user == null) {
            throw new AuthenticationException("ieml:"+imel+"不存在") ;
        }
//
//        if (!JWTUtil.verify(token, username, user.getPassword())) {
//            throw new AuthenticationException("账户密码错误!");
//        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
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

