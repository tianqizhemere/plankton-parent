package top.tianqi.plankton.config.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Wukh
 * @create 2021-01-10
 */
public class CustomizedToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -1707946953941377018L;
    /**
     * 登录类型
     */
    private String loginType;

    public CustomizedToken(final String username, final String password, String loginType){
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString(){
        return "loginType="+ loginType +",username=" + super.getUsername()+",password="+ String.valueOf(super.getPassword());
    }
}