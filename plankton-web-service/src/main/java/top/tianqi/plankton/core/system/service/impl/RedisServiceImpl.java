package top.tianqi.plankton.core.system.service.impl;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import top.tianqi.plankton.core.common.exception.RedisConnectException;
import top.tianqi.plankton.core.common.function.JedisExecutor;
import top.tianqi.plankton.util.JedisUtil;
import top.tianqi.plankton.core.system.entity.RedisInfo;
import top.tianqi.plankton.core.system.service.RedisService;

import java.util.*;

/**
 * redis服务层实现
 *
 * @author Wukh
 * @create 2021-03-15
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 换行符,用于解决Windows和Linux的换行区别
     */
    private static final String SEPARATOR = System.getProperty("line.separator");

    /**
     * 处理 jedis请求
     *
     * @param j 处理逻辑，通过 lambda行为参数化
     * @return 处理结果
     */
    private <T> T excuteByJedis(JedisExecutor<Jedis, T> j) throws RedisConnectException {
        try (Jedis jedis = JedisUtil.getJedis()) {
            return j.excute(jedis);
        } catch (Exception e) {
            throw new RedisConnectException(e.getMessage());
        }
    }


    @Override
    public List<RedisInfo> getRedisInfo() throws RedisConnectException {
        String info = this.excuteByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        List<RedisInfo> infoList = new ArrayList<>();
        String[] strs = Objects.requireNonNull(info).split(SEPARATOR);
        RedisInfo redisInfo;
        if (strs.length > 0) {
            for (String str1 : strs) {
                redisInfo = new RedisInfo();
                String[] str = str1.split(":");
                if (str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    redisInfo.setKey(key);
                    redisInfo.setValue(value);
                    infoList.add(redisInfo);
                }
            }
        }
        return infoList;
    }

    @Override
    public Map<String, Object> getKeysSize() throws RedisConnectException {
        Long dbSize = this.excuteByJedis(
                j -> {
                    Client client = j.getClient();
                    client.dbSize();
                    return client.getIntegerReply();
                }
        );
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() throws RedisConnectException {
        String info = this.excuteByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        String[] strs = Objects.requireNonNull(info).split(SEPARATOR);
        Map<String, Object> map = null;
        for (String s : strs) {
            String[] detail = s.split(":");
            if ("used_memory".equals(detail[0])) {
                map = new HashMap<>();
                map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", System.currentTimeMillis());
                break;
            }
        }
        return map;
    }
}
