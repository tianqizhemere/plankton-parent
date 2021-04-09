package top.tianqi.plankton.core.common.exception;

/**
 * 自定义401无权限异常(UnauthorizedException)
 * @author Wukh
 * @create 2021-01-04
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -2233508689423018641L;

    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}