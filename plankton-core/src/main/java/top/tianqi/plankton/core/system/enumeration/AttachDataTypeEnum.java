package top.tianqi.plankton.core.system.enumeration;

/**
 * 附件上传文件类型
 * @author Wukh
 * @create 2021-01-12
 */
public enum AttachDataTypeEnum {

    MODEL_APPLICATION(1, "机型更新"),
    EXTERNAL_APPLICATION(2, "外置应用");

    AttachDataTypeEnum(int code, String typeName) {
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
     * 根据类型获取编码
     * @param typeName 类型名称
     * @return code 编码
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
