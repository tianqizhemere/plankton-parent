package top.tianqi.plankton.config.jedis.function;

import top.tianqi.plankton.common.exception.RedisConnectException;

/**
 * jedis函数式接口
 * @author Wukh
 * @create 2021-03-15
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {

    R excute(T t) throws RedisConnectException;
}
