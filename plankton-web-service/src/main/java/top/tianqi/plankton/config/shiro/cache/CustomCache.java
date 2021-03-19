package top.tianqi.plankton.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import redis.clients.jedis.Jedis;
import top.tianqi.plankton.common.constant.Constant;
import top.tianqi.plankton.config.shiro.token.JwtUtil;
import top.tianqi.plankton.common.util.JedisUtil;
import top.tianqi.plankton.common.util.SerializableUtil;
import top.tianqi.plankton.common.utils.PropertiesUtil;

import java.util.*;

/**
 * 重写Shiro的Cache保存读取
 * @author tianQi
 * @create 2021-01-16
 */
public class CustomCache<K,V> implements Cache<K,V> {

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key shiro:cache:account
     * @return java.lang.String
     */
    private String getKey(Object key) {
        return Constant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), Constant.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(Boolean.FALSE.equals(JedisUtil.exists(this.getKey(key)))){
            return null;
        }
        return JedisUtil.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        PropertiesUtil.readProperties("config.properties");
        String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        // 设置Redis的Shiro缓存
        return JedisUtil.setObject(this.getKey(key), value, Integer.parseInt(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(Boolean.FALSE.equals(JedisUtil.exists(this.getKey(key)))){
            return null;
        }
        JedisUtil.delKey(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        Jedis jedis = JedisUtil.getJedis();
        Objects.requireNonNull(jedis).flushDB();
        jedis.close();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Jedis jedis = JedisUtil.getJedis();
        Long size = Objects.requireNonNull(jedis).dbSize();
        jedis.close();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Jedis jedis = JedisUtil.getJedis();
        Set<byte[]> keys = Objects.requireNonNull(jedis).keys("*".getBytes());
        Set<Object> set = new HashSet<>();
        for (byte[] bs : keys) {
            set.add(SerializableUtil.unserializable(bs));
        }
        jedis.close();
        return set;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys) {
            values.add(JedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
