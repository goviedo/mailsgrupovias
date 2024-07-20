package cl.goviedo.emails.grupovias.controller;

import cl.goviedo.emails.grupovias.utils.CronExpressionInterpreter;
import cl.goviedo.emails.grupovias.utils.Misc;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class QuartzController {

    private final Scheduler scheduler;

    @Autowired
    public QuartzController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping("/quartz-info")
    public Map<String, Object> getQuartzInfo() {
        Map<String, Object> info = new HashMap<>();
        try {
            info.put("schedulerName", scheduler.getSchedulerName());
            info.put("schedulerInstanceId", scheduler.getSchedulerInstanceId());
            info.put("threadPoolSize", scheduler.getMetaData().getThreadPoolSize());
            // Add other Quartz information as needed
        } catch (SchedulerException e) {
            info.put("error", "Error retrieving Quartz information: " + e.getMessage());
        }
        return info;
    }

    /**
     * Interpreta en idioma ingles una expresion cron
     * @param expresion
     * @return
     */
    @GetMapping("/interprete")
    public String interprete( @RequestParam(name = "expresion") String expresion) {
        return CronExpressionInterpreter.interpretCronExpression(expresion);
    }

    @GetMapping("/info/{id}")
    public Map<String, Object> getQuartzInfo(@PathVariable(name = "id") String jobsOrTriggers) {
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
                triggerInfo.put("nextFireTime", Misc.formatDateToTimeZone(trigger.getNextFireTime()));
                triggerInfo.put("previousFireTime", Misc.formatDateToTimeZone(trigger.getPreviousFireTime()));
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
}