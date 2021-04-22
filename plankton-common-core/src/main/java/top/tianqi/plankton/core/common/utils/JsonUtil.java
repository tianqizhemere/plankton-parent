package top.tianqi.plankton.core.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JsonUtil工具类
 * @Author wkh
 * @Date 2020/6/28 17:19
 */
public final class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 对象转json
     * @param obj 目标对象
     * @return json
     */
    public static String toJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.writeValueAsString(obj);
    }

    /**
     * 转换为 JSON 字符串，忽略空值
     * @param obj obj 目标对象
     * @return json
     */
    public static String toJsonStringIgnoreNull(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(obj);
    }



    /**
     * 集合对象转json集合
     * @param objects 多个同一类型对象
     * @return json集合
     */
    public static <T> List<String> listToListJson(List<T> objects) throws Exception {
        if (objects == null)
            return null;

        List<String> lists = new ArrayList<String>();
        for (T t : objects) {
            lists.add(toJsonString(t));
        }
        return lists;
    }

    /**
     * json转集合
     * @param jsons 多个json对象
     * @param c 转换类型
     * @return T
     */
    public static <T> List<T> listJsonToListObject(List<String> jsons, Class<T> c) throws Exception {
        if (jsons == null) {
            return null;
        }
        List<T> ts = new ArrayList<T>();
        for (String j : jsons) {
            ts.add(jsonToObject(j, c));
        }
        return ts;
    }

    /**
     * json转JavaBean
     * @param json json字符串
     * @param clazz 转换对象
     * @return T
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) throws Exception {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 字符串转换为 Map<String, Object>
     * @param json json字符串
     * @return Map 转换后的Map集合对象
     * @throws Exception
     */
    public static Map jsonToMap(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(json, Map.class);
    }

    /**
     * 字符串转换为 Map<String, T>
     * @param json json字符串
     * @param clazz 转换对象
     * @return Map 转换的Map集合
     */
    public static <T> Map<String, T> jsonToMap(String json, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(json, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToPojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * 深度转换 JSON 成 Map
     *
     * @param json json json字符串
     * @return
     */
    public static Map<String, Object> jsonToMapDeeply(String json) throws Exception {
        return jsonToMapRecursion(json, objectMapper);
    }

    /**
     * 把 JSON 解析成 List，如果 List 内部的元素存在 jsonString，继续解析
     *
     * @param json json字符串
     * @param mapper 解析工具
     * @return
     * @throws Exception
     */
    private static List<Object> jsonToListRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        List<Object> list = mapper.readValue(json, List.class);

        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = jsonToListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = jsonToMapRecursion(str, mapper);
                }
            }
        }
        return list;
    }

    /**
     * 把 JSON 解析成 Map，如果 Map 内部的 Value 存在 jsonString，继续解析
     *
     * @param json json字符串
     * @param mapper
     * @return
     * @throws Exception
     */
    private static Map<String, Object> jsonToMapRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }
        Map<String, Object> map = mapper.readValue(json, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);

                if (str.startsWith("[")) {
                    List<?> list = jsonToListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = jsonToMapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }
        return map;
    }

    /**
     * 将 JSON 数组转换为集合
     *
     * @param jsonArrayStr 集合json字符串
     * @param clazz 转换对象
     * @return
     * @throws Exception
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) throws Exception {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        return objectMapper.readValue(jsonArrayStr, javaType);
    }

    /**
     * 获取泛型的 Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 将 Map 转换为 JavaBean
     *
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T mapToPojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 将 Map 转换为 JSON
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将 JSON 对象转换为 JavaBean
     *
     * @param obj JavaBean
     * @param clazz 转换对象的字节码对象
     * @return
     */
    public static <T> T objToPojo(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }

    /**
     * json转任意复杂类型
     * jsonToObject(json, new TypeReference<DelayTask<Order>>() {});
     * @param json json字符串
     * @param tr 类型泛型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String json, TypeReference<T> tr) {
        if (!StringUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = objectMapper.readValue(json, tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
