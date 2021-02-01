package top.tianqi.plankton.system.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 外置应用类型
 * @author Wukh
 * @create 2021-02-01
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExternalTypeEnum {

    A_LI_PAY(1, "支付宝指纹支付"),
    TAO_BAO_PAY(2, "淘宝指纹支付"),
    WE_CHAT_PAY(3, "微信指纹支付"),
    QQ_PAY(4, "QQ指纹支付"),
    RIRU_CORE(5, "RiruCore核心");

    private ExternalTypeEnum(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    /** 编码 */
    private final int code;

    /** 名称 */
    private final String typeName;

    public int getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据编码获取名称
     * @param code code值
     * @return
     */
    public static String value(Integer code) {
        for (ExternalTypeEnum externalType : ExternalTypeEnum.values()) {
            if (externalType.getCode() == code) {
                return externalType.getTypeName();
            }
        }
        return null;
    }
}
