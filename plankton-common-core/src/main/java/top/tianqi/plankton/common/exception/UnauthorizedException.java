package top.tianqi.plankton.common.exception;

/**
 * 自定义401无权限异常(UnauthorizedException)
 * @author Wukh
 * @create 2021-01-04
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}
