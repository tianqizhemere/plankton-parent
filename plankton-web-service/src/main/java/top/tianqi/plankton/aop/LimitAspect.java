package top.tianqi.plankton.aop;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import top.tianqi.plankton.core.common.annotation.aop.Limit;
import top.tianqi.plankton.core.common.enumeration.LimitTypeEnum;
import top.tianqi.plankton.core.common.exception.LimitException;
import top.tianqi.plankton.util.JedisUtil;
import top.tianqi.plankton.util.AddressUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 接口限流切面类
 * @author Wukh
 * @create 2021-01-21
 */
@Aspect
@Component
public class LimitAspect {

    private static final Logger log = LoggerFactory.getLogger(LimitAspect.class);

    @Pointcut("@annotation(top.tianqi.plankton.core.common.annotation.aop.Limit)")
    public void pointcut() { }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitTypeEnum limitType = limitAnnotation.limitType();
        String key;
        String ip = AddressUtils.getRemoteIp(request);
        // 限流时间
        int limitPeriod = limitAnnotation.period();
        // 限流次数
        int limitCount = limitAnnotation.count();
        switch (limitType) {
            case IP:
                key = ip;
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnnotation.prefix() + "_", key, ip));
        String luaScript = this.buildLuaScript();
        Jedis jedis = JedisUtil.getJedis();
        Long result = (Long) jedis.evalsha(jedis.scriptLoad(luaScript), keys, Arrays.asList(Integer.toString(limitPeriod), Integer.toString(limitCount)));
        jedis.close();
        if (result > 0) {
            return point.proceed();
        } else {
            // 超过阈值
            log.error("触发限流====被限流的ip->{}", key);
            throw new LimitException();
        }
    }

    /**
     * 限流脚本
     * 调用的时候不超过阈值，则直接返回并执行计算器自加。
     *
     * <p>
     *     第一次使用incr对KEY（某个IP作为KEY）加一，
     *     如果是第一次访问，使用expire设置一个超时时间，
     *     这个超时时间作为Value第一个参数传入，
     *     如果现在递增的数目大于输入的第二个Value参数，
     *     返回失败标记，否则成功。redis的超时时间到了，这个Key消失，又可以访问啦。
     * </p>
     * <p>
     *  local num = redis.call('incr', KEYS[1])
     *  if tonumber(num) == 1 then
     *  	redis.call('expire', KEYS[1], ARGV[1])
     *  	return 1
     *  else if tonumber(num) > tonumber(ARGV[2]) then
     *  	return 0
     *  else
     *  	return 1
     *  end
     * </p>
     * @return lua脚本
     */
    private String buildLuaScript() {
        return "local num = redis.call('incr', KEYS[1])\n" +
                "if tonumber(num) == 1 then\n" +
                "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
                "\treturn 1\n" +
                "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
                "\treturn 0\n" +
                "else \n" +
                "\treturn 1\n" +
                "end\n";
    }
}
