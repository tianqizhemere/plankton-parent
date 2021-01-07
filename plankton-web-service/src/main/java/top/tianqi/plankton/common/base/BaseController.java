package top.tianqi.plankton.common.base;

import top.tianqi.plankton.common.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * 基类controller
 * @author Wukh
 * @create 2021-01-04
 */
public class BaseController {

    Map<String,String> orderTypeMap = new HashMap<>(2);

    protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

    public BaseController(){
        orderTypeMap.put("asc","asc");
        orderTypeMap.put("desc","desc");
    }

}
