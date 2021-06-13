package top.tianqi.plankton.quartz;


import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * 应用启动时调用一下QuartzTasks添加定时任务
 * @author Wukh
 * @create 2021-06-03
 */
@Configuration
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartApplicationListener.class);

    @Resource(name = "quartzTasks")
    private QuartzTasks quartzTasks;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止重复执行
        if (event.getApplicationContext().getParent() == null) {
            // 添加quartz定时任务
            logger.info("开始-添加quartz定时任务");
            try {
                quartzTasks.addQuartzJobs();
            } catch (SchedulerException e) {
                logger.error("添加quartz定时任务失败", e);
            }
            logger.info("结束-添加quartz定时任务");

            // 删除旧的不要的quartz定时任务
            logger.info("开始-删除旧的不要的quartz定时任务");
            try {
                quartzTasks.deleteQuartzJobs();
            } catch (SchedulerException e) {
                logger.error("删除旧的不要的quartz定时任务失败", e);
            }
            logger.info("结束-删除旧的不要的quartz定时任务");
        }
    }
}
