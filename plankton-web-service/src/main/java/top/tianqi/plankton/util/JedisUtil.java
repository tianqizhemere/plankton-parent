package top.tianqi.plankton.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.tianqi.plankton.core.common.constant.Constant;
import top.tianqi.plankton.core.common.exception.BusinessException;
import top.tianqi.plankton.core.common.utils.SerializableUtil;

import java.util.Set;

/**
 * JedisUtil(推荐存Byte数组，存Json字符串效率更慢)
 * @author tianQi
 * @create 2021-01-15
 */
@Component
public class JedisUtil {

    /**
     * 静态注入JedisPool连接池
     * 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
     * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
     */
    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    /**
     * 获取Jedis实例
     * @return Jedis
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("获取Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 释放Jedis资源
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new BusinessException("释放Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 获取redis键值-object
     * @param key 键
     * @return Object
     */
    public static Object getObject(String key) {
        try(Jedis jedis = jedisPool.getResource();) {
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null && bytes.length > 0) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            throw new BusinessException("获取Redis键值getObject方法异常:key=" + key + " cause=" + e.getMessage());
        }
        return null;
    }

    /**
     * 设置redis键值-object
     * @param key 键
     * @param value 值
     * @return String
     */
    public static String setObject(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            throw new BusinessException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * 设置redis键值-object-expireTime
     * @param key 键
     * @param value 值
     * @param expireTime 到期时间
     * @return String
     */
    public static String setObject(String key, Object value, int expireTime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (Constant.OK.equals(result)) {
                jedis.expire(key.getBytes(), expireTime);
            }
            return result;
        } catch (Exception e) {
            throw new BusinessException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * 获取redis键值-Json
     * @param key 键
     * @return Object
     */
    public static String getJson(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            throw new BusinessException("获取Redis键值getJson方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * 设置redis键值-Json
     * @param key 键
     * @param value 值
     * @return String
     */
    public static String setJson(String key, String value) {
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new BusinessException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * 设置redis键值-Json-expireTime
     * @param key 键
     * @param value 值
     * @param expireTime 到期时间
     * @return String
     */
    public static String setJson(String key, String value, int expireTime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key, value);
            if (Constant.OK.equals(result)) {
                jedis.expire(key, expireTime);
            }
            return result;
        } catch (Exception e) {
            throw new BusinessException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * 删除key
     * @param key 键
     * @return Long
     */
    public static Long delKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new BusinessException("删除Redis的键delKey方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * key是否存在
     * @param key 键
     * @return Boolean
     */
    public static Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            throw new BusinessException("查询Redis的键是否存在exists方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     * @param key 键
     * @return Set<String>
     */
    public static Set<String> keysS(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key);
        } catch (Exception e) {
            throw new BusinessException("模糊查询Redis的键集合keysS方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * 模糊查询获取key集合(keys的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用)
     * @param key 键
     * @return Set<String>
     */
    public static Set<byte[]> keysB(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key.getBytes());
        } catch (Exception e) {
            throw new BusinessException("模糊查询Redis的键集合keysB方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * 获取过期剩余时间
     * @param key 键
     * @return String
     */
    public static Long ttl(String key) {
        Long result = -2L;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            throw new BusinessException("获取Redis键过期剩余时间ttl方法异常:key=" + key + " cause=" + e.getMessage());
        }
    }
}
