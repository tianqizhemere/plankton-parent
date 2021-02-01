package top.tianqi.plankton.system.enumeration;

/**
 * 版本是否是最新枚举
 * @author Wukh
 * @create 2021-02-01
 */
public enum VersionTypeEnum {

    HISTORIC_VERSION(0, "历史版本"),
    THE_LATEST_VERSION(1, "最新版本");

    private VersionTypeEnum(int code, String typeName) {
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
        for (VersionTypeEnum versionType : VersionTypeEnum.values()) {
            if (versionType.getCode() == code) {
                return versionType.getTypeName();
            }
        }
        return null;
    }
}
