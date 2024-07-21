package cl.goviedo.emails.grupovias.services;

import cl.goviedo.emails.grupovias.jobs.EmailCompromisoUrgenciaJob;
import org.quartz.*;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Agrega un trabajo al ejecutar y lo envia
 * con intervalos en segundos
 */
@Service
public class JobService {

    private final Scheduler scheduler;

    public JobService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addJob(String jobName, String groupName, Class<? extends Job> jobClass, int intervalInSeconds) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, groupName)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", groupName)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(intervalInSeconds)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void addJobCron(String jobName, String groupName, Class<? extends Job> jobClass, String urgencia, String expresion, String descripcion) throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("urgencia", urgencia);

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, groupName).withDescription(descripcion)
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(expresion))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void addJobCronCompromisoUrgencia(
            String compromiso,
            String urgencia,
            String nombreJob,
            String cron,
            String destinatario,
            String listaDestinatarios,
            Class<? extends EmailCompromisoUrgenciaJob> jobClass)  throws SchedulerException {

        String grupo = "general";

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("urgencia", urgencia);
        jobDataMap.put("compromiso", compromiso);
        jobDataMap.put("destinatario", destinatario);
        jobDataMap.put("listaDestinatarios", listaDestinatarios);


        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(nombreJob, grupo).withDescription("Email generales, no tienen un destinatario en especial aun")
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(nombreJob + "Trigger", grupo)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
