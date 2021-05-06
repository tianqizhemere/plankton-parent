package top.tianqi.plankton.core.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 关于类的操作的工具类
 * @author Wukh
 * @create 2021-05-06
 */
public final class ClassUtil {

    private ClassUtil() {}

    /**
     * 获取类属性
     * @param targetObj 要获取属性的类
     * @return 含有类属性的集合
     */
    public static Field[] getClassAttribute(Object targetObj){
        Class<?> objectClass = targetObj.getClass();
        return objectClass.getDeclaredFields();
    }

    /**
     * 获取对象的所有get或set方法
     * @param targetObj 要获取属性的类
     * @param methodKeyword get或者set关键字
     * @return 含有类get或set方法的集合
     */
    public static List<Method> getMethod(Object targetObj, String methodKeyword){
        List<Method> methodList = new ArrayList<>();

        Class<?> objectClass = targetObj.getClass();

        Field[] field = objectClass.getDeclaredFields();
        for (Field value : field) {
            // 获取属性名并组装方法名
            String fieldName = value.getName();
            String getMethodName = methodKeyword
                    + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);

            try {
                Method method = objectClass.getMethod(getMethodName);
                methodList.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methodList;
    }

    /**
     * 获取对象的所有get方法
     * @param targetObj 要获取属性的类
     * @return 含有类方法的集合
     */
    public static List<Method> getMethodGet(Object targetObj){
        return getMethod(targetObj,"get");
    }

    /**
     * 获取对象的所有set方法
     * @param targetObj 要获取属性的类
     * @return 含有类方法的集合
     */
    public static List<Method> getMethodSet(Object targetObj){
        return getMethod(targetObj,"set");
    }
}
