package top.tianqi.plankton.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 生成随机字符串的工具类 uuid
 * @author Wukh
 * @create 2021-01-16
 */
public class UUIDUtils {

    /**
     * 获取一个UUID值
     * @return UUID值[String]
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取多个UUID值
     * @param number 所需个数
     * @return UUID集合
     */
    public List<String> getUUID(Integer number){
        List<String> list = new ArrayList<>();
        while (0 <= (number--)){
            list.add(getUUID());
        }
        return list;
    }
}
