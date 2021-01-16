package top.tianqi.plankton.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 重写Shiro缓存管理器
 * @author tianQi
 * @create 2021-01-16
 */
public class CustomCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<K,V>();
    }
}

