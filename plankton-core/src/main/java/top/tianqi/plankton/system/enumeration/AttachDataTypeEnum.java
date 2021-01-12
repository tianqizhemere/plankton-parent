package top.tianqi.plankton.system.enumeration;

/**
 *
 * @author Wukh
 * @create 2021-01-12
 */
public enum AttachDataTypeEnum {

    N9760(1, "N9760");

    private AttachDataTypeEnum(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    /** 编码 */
    private int code;

    /** 名称 */
    private String typeName;

    public int getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 根据编码获取名称
     * @param typeName
     * @return
     */
    public static Integer value(String typeName) {
        for (AttachDataTypeEnum t : AttachDataTypeEnum.values()) {
            if (t.getTypeName().equals(typeName)) {
                return t.getCode();
            }
        }
        return null;
    }
}
