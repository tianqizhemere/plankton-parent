package top.tianqi.plankton.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tianqi.plankton.common.Result;
import top.tianqi.plankton.common.controller.BaseController;
import top.tianqi.plankton.common.exception.RedisConnectException;
import top.tianqi.plankton.system.entity.RedisInfo;
import top.tianqi.plankton.system.service.RedisService;

import javax.annotation.Resource;
import java.util.List;

/**
 * redisController
 * @author Wukh
 * @create 2021-03-15
 */
@RestController
@RequestMapping("/system/redis")
public class RedisController extends BaseController {

    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    /**
     * 获取redis相关信息
     * @return Result 前端提示信息
     * @throws RedisConnectException Redis连接异常
     */
    @GetMapping("info")
    public Result getRedisInfo() throws RedisConnectException {
        List<RedisInfo> redisInfo = redisService.getRedisInfo();
        return SUCCESS_MESSAGE(redisInfo);
    }

    /**
     * 获取 redis key 数量
     * @return Result 前端提示信息
     * @throws RedisConnectException Redis连接异常
     */
    @GetMapping("keysSize")
    public Result getKeysSize() throws RedisConnectException {
        return SUCCESS_MESSAGE(redisService.getKeysSize());
    }

    /**
     * 获取 redis 内存信息
     * @return Result 前端提示信息
     * @throws RedisConnectException Redis连接异常
     */
    @GetMapping("memoryInfo")
    public Result getMemoryInfo() throws RedisConnectException {
        return SUCCESS_MESSAGE(redisService.getMemoryInfo());
    }
}
