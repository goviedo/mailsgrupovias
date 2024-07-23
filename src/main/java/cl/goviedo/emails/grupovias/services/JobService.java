package cl.goviedo.emails.grupovias.services;

import cl.goviedo.emails.grupovias.configuration.QuartzConfig;
import cl.goviedo.emails.grupovias.jobs.EmailCompromisoUrgenciaJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    public void addJobCronGrupoVias(
            @RequestParam("urgencia") String urgencia,
            @RequestParam("to") String to,
            @RequestParam("cc") String cc,
            String jobName,
            String groupName,
            Class<? extends Job> jobClass,
            String expresion,
            String descripcion) throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("urgencia", urgencia);
        jobDataMap.put("to", to);
        jobDataMap.put("cc", cc);

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
            String cuentaBancaria,
            Class<? extends EmailCompromisoUrgenciaJob> jobClass)  throws SchedulerException {

        String grupo = "general";

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("urgencia", urgencia);
        jobDataMap.put("compromiso", compromiso);
        jobDataMap.put("destinatario", destinatario);
        jobDataMap.put("listaDestinatarios", listaDestinatarios);
        jobDataMap.put("cuentaBancaria", cuentaBancaria); // Indica si colocar o no la cuenta bancaria (1 = SI)


        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(nombreJob, grupo).withDescription("Trabajo en especifico a correos.")
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(nombreJob + "Trigger", grupo).withDescription("Lanzamiento general a correos deseados, con cuenta corriente o no")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }


    /**
     * Elimina una tarea por su nombre y grupo
     * @param nombre
     * @param grupo
     * @throws SchedulerException
     */
    public void eliminarTareaNombreGrupo(String nombre, String grupo) throws SchedulerException {

        JobKey jKey = new JobKey(nombre, grupo);

        // Get the job's triggers
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jKey);

        // Unschedule all triggers associated with the job
        for (Trigger trigger : triggers) {
            scheduler.unscheduleJob(trigger.getKey());
        }

        // Delete the job
        scheduler.deleteJob(jKey);

        scheduler.deleteJob(new JobKey(nombre, grupo));
    }
}
