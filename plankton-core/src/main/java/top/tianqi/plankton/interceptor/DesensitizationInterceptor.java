package top.tianqi.plankton.interceptor;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import top.tianqi.plankton.anntation.Desensitization;
import top.tianqi.plankton.core.common.enumeration.DesensitizationTypeEnum;

import java.lang.reflect.Field;
import java.util.*;

/**
 * SQL拦截器，用于数据脱敏
 * TODO 待优化，脱敏后数据更新问题
 *      方案一，查询使用VO接收数据，避免更新操作
 * @author Wukh
 * @create 2021-03-24
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),})
public class DesensitizationInterceptor implements Interceptor {

    /** 是否启用脱敏*/
    @Value("${desensitization}")
    private boolean desensitization;

    private static final Logger logger = LoggerFactory.getLogger(DesensitizationInterceptor.class);

    private static final Map<String, DesensitizationTypeEnum> desensitionMap = new LinkedHashMap<>();

    static {
        initDesensitizationMap();
    }

    /**
     * 数据脱敏处理
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (desensitization) {
            // 先对Map进行处理
            if (result instanceof Map) {
                return this.desensitizationMap(result);
            }
            // 处理集合
            if (result instanceof ArrayList<?>) {
                List<?> list = (ArrayList<?>) result;
                return desensitization(list);
            } else {
                // 处理单个对象
                return desensitization(result);
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 用于在Mybatis配置文件中指定一些属性的，注册当前拦截器的时候可以设置一些属性
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 对集合脱敏
     * @param list 脱敏数据列表
     * @return 脱敏后的数据
     */
    private List<?> desensitization(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        Class cls = null;
        for (Object o : list) {
            // 脱敏map，改变引用地址(根据静态配置脱敏)
            if (o instanceof Map) {
                o = desensitizationMap(o);
                continue;
            }
            // 脱敏bean(根据注解脱敏)
            if (cls == null) {
                cls = o.getClass();
            }
            o = desensitization(o);
        }
        return list;
    }

    /*
     * 对map脱敏
     */
    private Object desensitizationMap(Object result) {
        Map mapResult = (Map) result;
        if (MapUtils.isEmpty(mapResult)) {
            return mapResult;
        }
        Set<String> keySet = mapResult.keySet();
        for (String key : keySet) {
            if (desensitionMap.containsKey(key)) {
                DesensitizationTypeEnum desensitionType = desensitionMap.get(key);
                String replacedVal = getReplacedVal(desensitionType, MapUtils.getString(mapResult, key), null);
                mapResult.put(key, replacedVal);
            }
        }
        return result;
    }


    private String getReplacedVal(DesensitizationTypeEnum type, String value, String[] attachs) {
        List<String> regular;
        switch (type) {
            case CUSTOM:
                regular = Arrays.asList(attachs);
                break;
            case TRUNCATE:
                regular = truncateRender(attachs);
                break;
            default:
                regular = Arrays.asList(type.getRegular());
        }
        if (regular.size() > 1) {
            String match = regular.get(0);
            String result = regular.get(1);
            if (null != match && result != null && match.length() > 0) {
                value = value.replaceAll(match, result);
                return value;
            }
        }
        return "";
    }

    /**
     * 自定义字符串脱敏
     * @param attachs
     * @return
     */
    private List<String> truncateRender(String[] attachs) {
        List<String> regular = new ArrayList<>();
        if (null != attachs && attachs.length > 1) {
            String rule = attachs[0];
            String size = attachs[1];
            String template, result;
            if ("0".equals(rule)) {
                template = "^(\\S{%s})(\\S+)$";
                result = "$1";
            } else if ("1".equals(rule)) {
                template = "^(\\S+)(\\S{%s})$";
                result = "$2";
            } else {
                return regular;
            }
            try {
                if (Integer.parseInt(size) > 0) {
                    regular.add(0, String.format(template, size));
                    regular.add(1, result);
                }
            } catch (Exception e) {
                logger.warn("ValueDesensitizeFilter truncateRender size {} exception", size);
            }
        }
        return regular;
    }

    /**
     * 初始化Map集合脱敏数据格式
     */
    private static void initDesensitizationMap() {
        desensitionMap.put("idCode", DesensitizationTypeEnum.ID_CARD);
        desensitionMap.put("idCard", DesensitizationTypeEnum.ID_CARD);
        desensitionMap.put("userIDCard", DesensitizationTypeEnum.ID_CARD);
        desensitionMap.put("userIdCard", DesensitizationTypeEnum.ID_CARD);
        desensitionMap.put("username", DesensitizationTypeEnum.NAME);
        desensitionMap.put("address", DesensitizationTypeEnum.ADDRESS);
    }

    /**
     * 对象脱敏
     * @param obj obj
     * @return
     */
    private Object desensitization(Object obj) {
        Class<?> cls;
        Field[] objFields;
        if (obj != null) {
            cls = obj.getClass();
            objFields = cls.getDeclaredFields();
            desensitizationField(obj, objFields);
        }
        return obj;
    }

    /**
     * 处理数据,对字段进行脱敏处理
     * @param obj 数据对象
     * @param objFields 对象字段数组
     */
    private void desensitizationField(Object obj, Field[] objFields) {
        for (Field field : objFields) {
            Desensitization desensitization;
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            if (String.class != field.getType() || (desensitization = field.getAnnotation(Desensitization.class)) == null) {
                continue;
            }
            field.setAccessible(true);
            String value;
            try {
                value = field.get(obj) != null ? field.get(obj).toString() : null;
            } catch (Exception e) {
                value = null;
            }
            if (value == null) {
                continue;
            }
            List<String> regular;
            // 获取脱敏规则类型
            DesensitizationTypeEnum type = desensitization.type();
            regular = Arrays.asList(type.getRegular());
            if (regular.size() > 1) {
                String match = regular.get(0);
                String result = regular.get(1);
                if (StringUtils.isNotBlank(match) && StringUtils.isNotBlank(result)) {
                    value = value.replaceAll(match, result);
                    try {
                        field.set(obj, value);
                    } catch (Exception ignore) {
                    }
                }
            }
        }
    }
}
