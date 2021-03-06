package top.tianqi.plankton.core.common.enumeration;

/**
 * 敏感词汇及脱敏规则枚举类
 * @author Wukh
 * @create 2021-3-1
 */
public enum DesensitizationTypeEnum {
    /** 手机号脱敏 */
    PHONE("phone", "11位手机号", "^(\\d{3})\\d{4}(\\d{4})$", "$1****$2"),
    /** 身份证号脱敏 */
    ID_CARD("idCard", "16或者18身份证号", "^(\\d{4})\\d{8,10}(\\w{4})$", "$1****$2"),
    /** 银行卡号脱敏 */
    BANK_CARD("bankCardNo", "银行卡号", "^(\\d{4})\\d*(\\d{4})$", "$1****$2"),
    /** 姓名脱敏 */
    NAME("name", "真实姓名", "(?<=.{1}).*(?=.{1})", "*"),
    /** 邮箱脱敏 */
    EMAIL("email", "电子邮箱", "(\\w+)\\w{5}@(\\w+)", "$1***@$2"),
    /** 地址脱敏 */
    ADDRESS("address", "地址", "(?<=.{3}).*(?=.{3})", "*"),
    /** 自定义规则 */
    CUSTOM("custom", "自定义正则处理", ""),
    TRUNCATE("truncate", "字符串截取处理", "");

    /** 脱敏类型 */
    String type;

    /** 脱敏描述 */
    String describe;

    /** 脱敏规则 */
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
