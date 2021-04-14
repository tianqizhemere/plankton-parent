package top.tianqi.plankton.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import top.tianqi.plankton.anntation.Desensitization;
import top.tianqi.plankton.core.common.enumeration.DesensitizationTypeEnum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * SQL拦截器，用于数据脱敏
 * @author Wukh
 * @create 2021-03-24
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),})
public class DesensitizationInterceptor implements Interceptor {

    /**
     * 数据脱敏处理
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof ArrayList<?>) {
            List<?> list = (ArrayList<?>) result;
            return desensitization(list);
        } else {
            return desensitization(result);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

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
