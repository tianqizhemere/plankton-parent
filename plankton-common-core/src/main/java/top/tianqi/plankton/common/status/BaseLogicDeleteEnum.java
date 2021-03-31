package top.tianqi.plankton.common.status;

/**
 * 基础逻辑删除枚举类
 * 配合Mybatis Plus使用
 * @author Wukh
 * @create 2021-01-04
 */
public enum BaseLogicDeleteEnum {

    /**
     * 逻辑删除
     */
    LOGIC_DELETE("删除","1"),

    /**
     * 未删除
     */
    LOGIC_NOT_DELETE("未删除","0");

    private final String name;
    private final String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    BaseLogicDeleteEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
