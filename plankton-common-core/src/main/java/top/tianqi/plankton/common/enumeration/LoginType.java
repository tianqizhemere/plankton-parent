package top.tianqi.plankton.common.enumeration;

/**
 * @author Wukh
 * @create 2021-01-10
 */
public enum LoginType {

    /**
     * 密码登录
     */
    PASSWORD_LOGIN_TYPE("Password"),
    /**
     * 验证码登录
     */
    CODE_LOGIN_TYPE("Code");

    private String type;

    LoginType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
