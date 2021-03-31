package top.tianqi.plankton.common.enumeration;

/**
 * 敏感词汇枚举类
 * @author Wukh
 * @create 2021-3-1
 */
public enum DesensitizationTypeEnum {
    /**
     * 手机号脱敏
     */
    PHONE("phone", "11位手机号", "^(\\d{3})\\d{4}(\\d{4})$", "$1****$2"),
    /**
     * 身份证号脱敏
     */
    ID_CARD("idCard", "16或者18身份证号", "^(\\d{4})\\d{8,10}(\\w{4})$", "$1****$2"),
    /**
     * 银行卡号脱敏
     */
    BANK_CARD("bankCardNo", "银行卡号", "^(\\d{4})\\d*(\\d{4})$", "$1****$2"),
    /**
     * 姓名脱敏
     */
    NAME("name", "真实姓名", "(?<=.{1}).*(?=.{1})", "*"),
    /**
     * 邮箱脱敏
     */
    EMAIL("email", "电子邮箱", "(\\w+)\\w{5}@(\\w+)", "$1***@$2");

    String type;
    String describe;
    String[] regular;

    public String getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }

    public String[] getRegular() {
        return regular;
    }

    DesensitizationTypeEnum(String type, String describe, String... regular) {
        this.type = type;
        this.describe = describe;
        this.regular = regular;
    }
}
