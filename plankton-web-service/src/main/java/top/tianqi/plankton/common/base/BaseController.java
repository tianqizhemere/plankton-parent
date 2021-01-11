package top.tianqi.plankton.common.base;

import top.tianqi.plankton.common.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 基类controller
 * @author Wukh
 * @create 2021-01-04
 */
public class BaseController {

    Map<String,String> orderTypeMap = new HashMap<>(2);

    public BaseController(){
        orderTypeMap.put("asc","asc");
        orderTypeMap.put("desc","desc");
    }

    /**
     * 成功信息
     * @param data 返回对象
     * @return
     */
    protected static Result SUCCESS_MESSAGE(Object data) {
        return Result.success("操作成功", data);
    }

    protected static Result SUCCESS_MESSAGE() {
        return SUCCESS_MESSAGE(null);
    }

    /**
     * 错误信息
     * @param msg
     * @return
     */
    protected static Result ERROR_MESSAGE(String msg) {
        return Result.error(msg);
    }

    /**
     * 错误信息
     * @return
     */
    protected static Result ERROR_MESSAGE() {
        return Result.error();
    }

}
