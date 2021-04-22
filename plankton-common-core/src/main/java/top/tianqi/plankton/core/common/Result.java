package top.tianqi.plankton.core.common;

import top.tianqi.plankton.core.common.enumeration.ErrorStateEnum;

import java.util.HashMap;

/**
 * 返回数据格式
 * @author Wukh
 * @create 2021-01-08
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -3990768377677695712L;

    /** 状态码 */
    private static final String CODE_TAG = "code";

    /** 返回内容 */
    private static final String MSG_TAG = "massage";

    /** 数据对象 */
    private static final String DATA_TAG = "data";

    /** 是否成功 */
    private static final String SUCCESS_TAG = "success";

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
        super.put(SUCCESS_TAG, Boolean.TRUE);
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
        super.put(SUCCESS_TAG, Boolean.TRUE);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param isSuccess 是否操作成功
     */
    public Result(int code, String msg, boolean isSuccess) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(SUCCESS_TAG, isSuccess);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     * @param isSuccess 是否操作成功
     */
    public Result(int code, String msg, Object data, boolean isSuccess) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
        super.put(SUCCESS_TAG, isSuccess);
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
        return new Result(ErrorStateEnum.REQUEST_SUCCESS.getCode(), msg, data, Boolean.TRUE);
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
        return Result.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data) {
        return new Result(ErrorStateEnum.REQUEST_ERROR.getCode(), msg, data, Boolean.FALSE);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static Result error(int code, String msg) {
        return new Result(code, msg, null, Boolean.FALSE);
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @return 警告消息
     */
    public static Result error(ErrorStateEnum errorState) {
        return Result.error(errorState.getCode(), errorState.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param errorState  错误信息枚举类
     * @param data  返回内容
     * @return 警告消息
     */
    public static Result error(ErrorStateEnum errorState, Object data) {
        return new Result(errorState.getCode(), errorState.getMsg(), data, Boolean.FALSE);
    }
}
