package top.tianqi.plankton.common;

import org.apache.http.HttpStatus;
import top.tianqi.plankton.common.status.ErrorStateEnum;

import java.util.HashMap;

/**
 * 返回数据格式
 * @author Wukh
 * @create 2021-01-08
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -3990768377677695712L;
    /**
     * 状态码
     */
    private static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    private static final String MSG_TAG = "massage";

    /**
     * 数据对象
     */
    private static final String DATA_TAG = "data";

    /**
     * 是否成功
     */
    private static final String IS_SUCCESS = "success";

    /**
     * 初始化一个新创建的 Result 对象，使其表示一个空消息。
     */
    public Result() {
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public Result(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(IS_SUCCESS, Boolean.TRUE);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Result(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
        super.put(IS_SUCCESS, Boolean.TRUE);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result success() {
        return Result.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static Result success(Object data) {
        return Result.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Result success(String msg) {
        return Result.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Result success(String msg, Object data) {
        return new Result(HttpStatus.SC_OK, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static Result error() {
        return Result.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result error(String msg) {
        Result error = Result.error(msg, null);
        error.put(IS_SUCCESS,  Boolean.FALSE);
        return error;
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data) {
        Result result = new Result(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg, data);
        result.put(IS_SUCCESS, Boolean.FALSE);
        return result;
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static Result error(int code, String msg) {
        Result result = new Result(code, msg, null);
        result.put(IS_SUCCESS, Boolean.FALSE);
        return result;
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @return 警告消息
     */
    public static Result error(ErrorStateEnum errorState) {
        Result error = Result.error(errorState.getCode(), errorState.getMsg());
        error.put(IS_SUCCESS, Boolean.FALSE);
        return error;
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @param data  返回内容
     * @return 警告消息
     */
    public static Result error(ErrorStateEnum errorState, Object data) {
        Result result = new Result(errorState.getCode(), errorState.getMsg(), data);
        result.put(IS_SUCCESS, Boolean.FALSE);
        return result;
    }
}
