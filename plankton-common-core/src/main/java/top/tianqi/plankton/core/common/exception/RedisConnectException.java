package top.tianqi.plankton.core.common.exception;

/**
 * Redis连接异常
 * @author Wukh
 * @create 2021-03-15
 */
public class RedisConnectException extends Exception {

    private static final long serialVersionUID = -8647050367246243260L;

    public RedisConnectException(String message) {
        super(message);
    }
}
