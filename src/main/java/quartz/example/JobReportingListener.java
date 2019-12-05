package quartz.example;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class JobReportingListener implements JobListener {

    public static final String REPORTING_LISTENER_NAME = "ADMIN_REPORTING_LISTENER";

    public String getName() {
        return REPORTING_LISTENER_NAME;
    }

    public void jobToBeExecuted(final JobExecutionContext jobExecutionContext) {
        System.out.println("-------------------------JobReportingListener 1----------------------------");
        final String jobName = jobExecutionContext.getJobDetail().getKey().toString();
        System.out.println("JobReportingListener ... jobToBeExecuted(): " + jobName + " is starting...");
        System.out.println("-------------------------JobReportingListener 1----------------------------");
    }

    public void jobExecutionVetoed(final JobExecutionContext jobExecutionContext) {
        System.out.println("-------------------------JobReportingListener 2----------------------------");
        System.out.println("JobReportingListener ... jobExecutionVetoed()");
    }

    public void jobWasExecuted(final JobExecutionContext jobExecutionContext, final JobExecutionException jobException) {
        System.out.println("-------------------------JobReportingListener 3----------------------------");
        System.out.println("JobReportingListener ... jobWasExecuted()");

        final String jobName = jobExecutionContext.getJobDetail().getKey().toString();
        System.out.println("Job : " + jobName + " is finished!!");

        if (!jobException.getMessage().equals("")) {
            System.out.println("Exception thrown by: " + jobName + " Exception: " + jobException.getMessage());
        }
    }
}
