package top.tianqi.plankton.config.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwtToken
 * @author Wukh
 * @create 2021-01-10
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = -7505098618144167329L;

    /** 秘钥 */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
