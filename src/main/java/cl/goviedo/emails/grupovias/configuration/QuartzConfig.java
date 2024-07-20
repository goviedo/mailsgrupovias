package cl.goviedo.emails.grupovias.configuration;

import cl.goviedo.emails.grupovias.jobs.EmailGrupoViasJob;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Value("${email.intervalo}")
    private String intervalo;

    private final JobFactory jobFactory;

    public QuartzConfig(JobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @Bean
    public JobDetailFactoryBean  emailJobDetail() {

        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(EmailGrupoViasJob.class);
        jobDetailFactory.setName("grupovias");
        jobDetailFactory.setDescription("Email mios enviados en un intervalo predefinidos a los irresponsables de grupo vias");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
        /*
        return JobBuilder.newJob(EmailGrupoViasJob.class)
                .withIdentity("emailJob")
                .storeDurably()
                .build();*/
    }

    @Bean
    public CronTriggerFactoryBean trigger(JobDetail job) {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setJobDetail(job);
        triggerFactory.setCronExpression(intervalo); // Run every 5 minutes
        return triggerFactory;
    }
/*
    @Bean
    public Trigger emailJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(intervalo) // every 2 days
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail())
                .withIdentity("emailTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }*/

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);

        // TIMEZONE
        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.timeZone", "America/Santiago");
        factory.setQuartzProperties(quartzProperties);
        return factory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
