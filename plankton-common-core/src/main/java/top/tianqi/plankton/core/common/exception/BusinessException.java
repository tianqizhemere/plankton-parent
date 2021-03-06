package top.tianqi.plankton.core.common.exception;

import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;

/**
 * 自定义业务异常
 * @author Wukh
 * @create 2021-01-04
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误编码 */
    private Integer errorCode = 500;

    /** 消息是否为属性文件中的Key */
    private boolean propertiesKey = true;

    /** 构造一个基本异常 */
    public BusinessException(){
        super();
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 描述信息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param o StatusInfoEnum枚举类
     */
    public BusinessException(ErrorStateEnum o) {
        super(o.getMsg());
        this.setErrorCode(o.getCode());
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 状态码
     * @param message 异常信息
     */
    public BusinessException(Integer errorCode, String message) {
        this(errorCode, message, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 状态码
     * @param message 异常信息
     * @param cause 异常原因
     */
    public BusinessException(Integer errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 状态码
     * @param message 异常信息
     * @param propertiesKey 是否为properties文件中的key
     */
    public BusinessException(Integer errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 异常状态码
     * @param message 异常信息
     * @param cause 异常原因
     * @param propertiesKey 是否为properties文件中的key
     */
    public BusinessException(Integer errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 异常信息
     * @param cause 异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }
}
