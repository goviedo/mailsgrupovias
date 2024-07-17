package cl.goviedo.emails.grupovias.configuration;

import cl.goviedo.emails.grupovias.jobs.EmailJob;
import cl.goviedo.emails.grupovias.utils.Misc;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail emailJobDetail() {
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity("emailJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(Misc.cadaDosMinutos()) // every 2 days
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail())
                .withIdentity("emailTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}