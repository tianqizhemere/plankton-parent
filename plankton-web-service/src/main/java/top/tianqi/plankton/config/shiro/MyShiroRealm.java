package top.tianqi.plankton.config.shiro;

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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.config.shiro.token.JwtToken;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.system.entity.User;
import top.tianqi.plankton.system.service.AuthService;
import top.tianqi.plankton.system.service.UserService;

import java.util.Objects;
import java.util.Set;

/**
 * 自定义 realm
 * @author Wukh
 * @create 2021-01-04
 */
@Component
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
        // 解密获得code，用于和数据库进行对比
        String code = JwtUtil.getClaim(token, Constant.ACCOUNT);
        if (code == null) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        User user = userService.getUser(code);
        if (user == null) {
            throw new AuthenticationException("UUID:"+code+"不存在") ;
        }
        if (Constant.USER_FREEZE.equals(user.getIsEnable())){
            throw new AuthenticationException("账号已被禁用,请联系管理员!");
        }
        if (!Objects.equals(user.getModel(), user.getModel())) {
            throw new AuthenticationException("设备型号不一致");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + code)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + code).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                    return new SimpleAuthenticationInfo(token, token, getName());
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
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
        String code = JwtUtil.getClaim(principals.toString(), Constant.ACCOUNT);
        if (!StringUtils.isEmpty(code)) {
            User user = userService.getUser(code);
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

