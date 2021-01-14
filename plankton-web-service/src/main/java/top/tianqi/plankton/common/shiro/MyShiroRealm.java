package top.tianqi.plankton.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.shiro.token.JwtToken;
import top.tianqi.plankton.common.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.AuthService;
import top.tianqi.plankton.system.service.UserService;

import java.util.Set;

/**
 * 自定义 realm
 * @author Wukh
 * @create 2021-01-04
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

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
        if (Constant.USER_FREEZE.equals(user.getIsEnable())){
            throw new AuthenticationException("账号已被禁用,请联系管理员!");
        }
//
//        if (!JWTUtil.verify(token, username, user.getPassword())) {
//            throw new AuthenticationException("账户密码错误!");
//        }
        return new SimpleAuthenticationInfo(token, token, imel);
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
        String imel = JwtUtil.getImel(principals.toString());
        System.out.println(imel);
        if (!StringUtils.isEmpty(imel)) {
            User user = userService.getUser(imel);
            if (user == null) {
                log.error("授权失败，用户信息为空！！！");
                return null;
            }
            // 查询权限
            Set<String> auths = authService.getUserAuthListById(user.getId());
            if (!CollectionUtils.isEmpty(auths)){
                // 构造权限后返回
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                // 构造权限
                authorizationInfo.addStringPermissions(auths);
                return authorizationInfo;
            }
        }
        return null;
    }
}

