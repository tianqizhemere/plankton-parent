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
     * @param object 返回对象
     * @return
     */
    protected static Result SUCCESS_MESSAGE(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    protected static Result SUCCESS_MESSAGE() {
        return SUCCESS_MESSAGE(null);
    }

    /**
     * 错误信息
     * @param code
     * @param msg
     * @return
     */
    protected static Result ERROR_MESSAGE(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
