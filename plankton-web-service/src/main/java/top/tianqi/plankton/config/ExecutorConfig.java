package top.tianqi.plankton.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 * @author Wukh
 * @create 2021-04-25
 */
@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer {

    /** 核心线程数 */
    @Value("${taskPool.core-size}")
    private Integer corePoolSize;

    /** 最大线程数 */
    @Value("${taskPool.max-size}")
    private Integer maxPoolSize;

    /** 缓冲队列数 */
    @Value("${taskPool.queue-capacity}")
    private Integer queueCapacity;

    /** 线程池名前缀 */
    @Value("${taskPool.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
