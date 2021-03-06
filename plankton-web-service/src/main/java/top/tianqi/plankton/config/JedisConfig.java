package top.tianqi.plankton.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置，项目启动注入JedisPool
 * @author tianQi
 * @create 2021-01-15
 */
@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "redis")
public class JedisConfig {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    /** 连接地址 */
    private String host;

    /** 连接端口 */
    private int port;

    /** redis连接密码 */
    private String password;

    /** 连接超时时间（毫秒） */
    private int timeout;

    /** 连接池最大连接数 */
    @Value("${redis.pool.max-active}")
    private int maxActive;

    /** 连接池最大阻塞等待时间 */
    @Value("${redis.pool.max-wait}")
    private int maxWait;

    /** 最大连接数 */
    @Value("${redis.pool.max-total}")
    private int maxTotal;

    /** 连接池中的最大空闲连接 */
    @Value("${redis.pool.max-idle}")
    private int maxIdle;

    /** 连接池中的最小空闲连接 */
    @Value("${redis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisPool redisPoolFactory() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            jedisPoolConfig.setMaxTotal(maxTotal);
            // 检测对象的有效性，检测空闲对象线程检测到对象不需要移除时
            jedisPoolConfig.setTestWhileIdle(true);
            // 密码为空设置为null
            if (StringUtils.isBlank(password)) {
                password = null;
            }
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
            logger.info("初始化Redis连接池JedisPool成功!地址: {}:{}", host, port);
            return jedisPool;
        } catch (Exception e) {
            logger.error("初始化Redis连接池JedisPool异常:{}", e.getMessage());
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
}
