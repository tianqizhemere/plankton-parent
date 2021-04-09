package top.tianqi.plankton.core.system.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据操作类型
 * @author Wukh
 * @create 2021-02-04
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationTypeEnum {

    INSERT(1,"新增"),
    SELECT(2,"查询"),
    UPDATE(3,"修改"),
    DELETE(4,"删除"),
    LOGIN(5,"登录");

    private OperationTypeEnum(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }
    /** 名称 */
    private final String typeName;

    /** code值 */
    private final int code;

    public String getTypeName() {
        return typeName;
    }


    public int getCode() {
        return code;
    }
}
