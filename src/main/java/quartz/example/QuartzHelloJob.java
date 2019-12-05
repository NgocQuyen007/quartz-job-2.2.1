package quartz.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzHelloJob implements Job {
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // run this job at the specific time
        System.out.println("Job is executing ....");
    }
}
