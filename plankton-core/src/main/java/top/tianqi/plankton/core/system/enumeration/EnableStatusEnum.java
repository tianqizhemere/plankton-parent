package top.tianqi.plankton.core.system.enumeration;

/**
 * 启用状态枚举类
 * @author Wukh
 * @create 2021-02-23
 */
public enum EnableStatusEnum {

    USER_UNFREEZE(true, "启用"),
    USER_FREEZE(false, "停用");

    EnableStatusEnum(boolean code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    /** 编码 */
    private final boolean code;

    /** 名称 */
    private final String typeName;

    public boolean getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据类型获取编码
     * @param typeName 类型名称
     * @return code 编码
     */
    public static String value(boolean code) {
        for (EnableStatusEnum enableStatus : EnableStatusEnum.values()) {
            if (code == enableStatus.getCode()) {
                return enableStatus.getTypeName();
            }
        }
        return null;
    }
}
