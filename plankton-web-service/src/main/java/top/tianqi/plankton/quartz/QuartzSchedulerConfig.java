package top.tianqi.plankton.quartz;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Wukh
 * @create 2021-06-03
 */
@Configuration
public class QuartzSchedulerConfig implements SchedulerFactoryBeanCustomizer {

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 延时10s启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        schedulerFactoryBean.setStartupDelay(10);
        // 自动启动
        schedulerFactoryBean.setAutoStartup(true);
        // 覆盖已存在的任务,用于Quartz集群，QuartzScheduler启动会更新已存在的Job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}
