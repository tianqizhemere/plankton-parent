package top.tianqi.plankton.quartz;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * 启动时添加或更新所有定时任务
 * @author Wukh
 * @create 2021-06-03
 */
@Component
@PropertySource("classpath:quartz.properties")
public class QuartzTasks {

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 默认时区
     */
    private static final TimeZone defaultTimeZone = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 任务分组
     */
    private static final String GROUP_A = "GroupA";


    /**
     * 添加定时任务
     *
     * @throws SchedulerException
     */
    public void addQuartzJobs() throws SchedulerException {
        // 添加定时任务
      /*  addJob(scheduler, MyJob.class, GROUP_A , "myJob1", "* * 0 01 01 ? *");
        addJob(scheduler, ModuleTaskJob.class, GROUP_A, "moduleTaskJob1", "0 0 1 * * ?");*/
    }

    /**
     * 删除旧的不要的定时任务
     *
     * @throws SchedulerException
     */
    public void deleteQuartzJobs() throws SchedulerException {
        // 删除旧的不要的定时任务
        // deleteJob(scheduler, groupName, jobName);
    }

    /**
     * 添加定时任务
     *
     * @param scheduler Quartz调度器
     * @param jobClass 任务类
     * @param groupName 任务分组
     * @param jobName 任务名
     * @param jobCron 任务定时cron
     * @throws SchedulerException
     */
    private void addJob(Scheduler scheduler, Class<? extends Job> jobClass, String groupName, String jobName,
                        String jobCron) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                // 任务名称和任务分组 组合成任务唯一标识
                .withIdentity("Job" + jobName, "Job" + groupName)
                // 无触发器指向时是否需要持久化
                .storeDurably(false).build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                // 触发器名称和触发器分组 组合成触发器唯一标识
                .withIdentity("Trigger" + jobName, "Trigger" + groupName)
                // cron触发器时间设定
                .withSchedule(CronScheduleBuilder.cronSchedule(jobCron).inTimeZone(defaultTimeZone))
                .build();
        TriggerKey triggerKey = TriggerKey.triggerKey("Trigger" + jobName, "Trigger" + groupName);
        // 如果已经存在定时任务
        if (scheduler.checkExists(triggerKey)) {
            // 更新定时任务的cron
            scheduler.rescheduleJob(triggerKey, trigger);
        } else {
            // 添加定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler Quartz调度器
     * @param groupName 任务分组
     * @param jobName 任务名
     * @throws SchedulerException
     */
    private void deleteJob(Scheduler scheduler, String groupName, String jobName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey("Trigger" + jobName, "Trigger" + groupName);
        // 如果已经存在定时任务
        if (scheduler.checkExists(triggerKey)) {
            // 删除触发器,如果对应的任务没有其他的触发器关联并且Durably=false的时候,同时删除对应的任务
            scheduler.unscheduleJob(triggerKey);
        }
    }
}
