package top.tianqi.plankton.web.system.service;

import top.tianqi.plankton.core.common.exception.RedisConnectException;
import top.tianqi.plankton.core.system.entity.RedisInfo;

import java.util.List;
import java.util.Map;

/**
 * redis服务层接口
 *
 * @author Wukh
 * @create 2021-03-15
 */
public interface RedisService {

    /**
     * 获取 redis 的详细信息
     *
     * @return List
     */
    List<RedisInfo> getRedisInfo() throws RedisConnectException;

    /**
     * 获取 redis key 数量
     *
     * @return Map
     */
    Map<String, Object> getKeysSize() throws RedisConnectException;

    /**
     * 获取 redis 内存信息
     *
     * @return Map
     */
    Map<String, Object> getMemoryInfo() throws RedisConnectException;
}
