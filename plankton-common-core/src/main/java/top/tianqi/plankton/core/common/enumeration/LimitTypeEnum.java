package top.tianqi.plankton.core.common.enumeration;

/**
 * 接口限流类型
 * @author Wukh
 * @create 2021-01-21
 */
public enum LimitTypeEnum {

    /** 传统类型 */
    CUSTOMER,
    /** 根据 IP 限制 */
    IP;
}
