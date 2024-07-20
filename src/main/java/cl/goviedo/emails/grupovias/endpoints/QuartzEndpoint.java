package cl.goviedo.emails.grupovias.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Endpoint(id = "quartz")
@Slf4j
public class QuartzEndpoint {

    private Scheduler scheduler;

    @Autowired
    public QuartzEndpoint(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @ReadOperation
    public Map<String, Object> getQuartzInfo(@Selector String jobsOrTriggers) {
        log.info("TriggerList");
        Map<String, Object> info = new HashMap<>();
        try {
            switch (jobsOrTriggers.toLowerCase()) {
                case "jobs":
                    info.put("jobs", getJobsInfo());
                    break;
                case "triggers":
                    info.put("triggers", getTriggersInfo());
                    break;
                default:
                    info.put("error", "Invalid selector. Use 'jobs' or 'triggers'.");
            }
        } catch (SchedulerException e) {
            info.put("error", "Error retrieving Quartz information: " + e.getMessage());
        }
        return info;
    }

    private List<Map<String, Object>> getTriggersInfo() throws SchedulerException {
        List<Map<String, Object>> triggersList = new ArrayList<>();
        for (String groupName : scheduler.getTriggerGroupNames()) {
            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName))) {
                Map<String, Object> triggerInfo = new HashMap<>();
                Trigger trigger = scheduler.getTrigger(triggerKey);
                triggerInfo.put("name", triggerKey.getName());
                triggerInfo.put("group", triggerKey.getGroup());
                triggerInfo.put("description", trigger.getDescription());
                triggerInfo.put("nextFireTime", trigger.getNextFireTime());
                triggerInfo.put("previousFireTime", trigger.getPreviousFireTime());
                triggersList.add(triggerInfo);
            }
        }
        log.info("TriggerList");
        log.info(triggersList.toString());
        return triggersList;
    }

    private List<Map<String, Object>> getJobsInfo() throws SchedulerException {
        List<Map<String, Object>> jobsList = new ArrayList<>();
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                Map<String, Object> jobInfo = new HashMap<>();
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                jobInfo.put("name", jobKey.getName());
                jobInfo.put("group", jobKey.getGroup());
                jobInfo.put("description", jobDetail.getDescription());
                jobInfo.put("jobClass", jobDetail.getJobClass().getName());
                jobsList.add(jobInfo);
            }
        }
        return jobsList;
    }

    private int getJobCount() throws SchedulerException {
        int count = 0;
        for (String groupName : scheduler.getJobGroupNames()) {
            count += scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)).size();
        }
        return count;
    }

    private int getTriggerCount() throws SchedulerException {
        int count = 0;
        for (String groupName : scheduler.getTriggerGroupNames()) {
            count += scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName)).size();
        }
        return count;
    }

    private List<String> getCurrentlyExecutingJobs() throws SchedulerException {
        List<String> executingJobs = new ArrayList<>();
        for (JobExecutionContext context : scheduler.getCurrentlyExecutingJobs()) {
            executingJobs.add(context.getJobDetail().getKey().getName());
        }
        return executingJobs;
    }
}
