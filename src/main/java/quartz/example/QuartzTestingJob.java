package quartz.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzTestingJob implements Job {
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Testing is executing ....");
    }
}
