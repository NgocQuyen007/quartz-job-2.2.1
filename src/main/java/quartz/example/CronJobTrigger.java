package quartz.example;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.matchers.NameMatcher;

public class CronJobTrigger {

    public static final String REPORTING_LISTENER_NAME = "ADMIN_REPORTING_LISTENER";

    public static void main(String[] args) throws Exception {

        final String scheduleGroup = "group1";
        JobReportingListener jobReportingListener = new JobReportingListener();

        final JobKey jobKey = new JobKey(QuartzHelloJob.class.getSimpleName(), scheduleGroup);
        final JobDetail jobDetail = JobBuilder.newJob(QuartzHelloJob.class).withIdentity(jobKey).build();

        // final JobKey jobKeyTesting = new JobKey(QuartzTestingJob.class.getSimpleName(), scheduleGroup);
        // final JobDetail jobDetailTesting = JobBuilder.newJob(QuartzTestingJob.class).withIdentity(jobKeyTesting).build();

        // 0/5 <=> expression now/now_later
        // every 5 second: 0/5 * * * * ?
        // every minute: 0 * * * * ?
        // every friday 23:59:59: 59 59 23 ? * FRI
        // every wednesday: 0 35 15 ? * WED
        final Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("tigerTrigger: " + QuartzHelloJob.class.getSimpleName(), scheduleGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule("50 46 10 ? * *"))
                .build();

        // QuartzScheduler StdScheduler
        final Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // initial
        // scheduler.getListenerManager().addJobListener(jobReportingListener);
        scheduler.getListenerManager().addJobListener(jobReportingListener, NameMatcher.jobNameEquals("%&%"));

        // scheduleJob
        scheduler.getListenerManager().addJobListenerMatcher(REPORTING_LISTENER_NAME, KeyMatcher.keyEquals(jobDetail.getKey()));

        scheduler.start();

        // store jobDetail in JobExecutionContext
        // JobKey should be the same in JobDetail<JobKey>:JobExecutionContextImpl and Matcher<JobKey>:globalJobListenerMatcher
        // Quartz #notifyJobListenersToBeExecuted #if(!matchJobListener(jl, jec.getJobDetail().getKey()))
        // scheduler.scheduleJob(jobDetailTesting, trigger);
        scheduler.scheduleJob(jobDetail, trigger);

        // JobExecutionContext        1#JobDetail # JobKey => insert from scheduler.scheduleJob(jobDetail, trigger);
        // GlobalJobListenerMatcher   n#Matcher   # JobKey => insert from addJobListener[matcher]
    }
}
