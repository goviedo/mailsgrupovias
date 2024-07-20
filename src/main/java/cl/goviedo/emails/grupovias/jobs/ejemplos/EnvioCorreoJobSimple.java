package cl.goviedo.emails.grupovias.jobs.ejemplos;

import cl.goviedo.emails.grupovias.services.correo.GoogleJavaMailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Job.
 * Tomar en especial cuidado aqui. Quizas por el reflection o algo similar
 * no se puede usar el constructor para hacer inyeccion de la dependencia
 * de la clase GoogleJavaMailService, ya que el que llama a este job
 * necesita de un constructor sin argumentos.
 */
@Component
@Slf4j
public class EnvioCorreoJobSimple implements Job {

    @Autowired
    private  GoogleJavaMailService emailService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getJobDetail().getKey().getName();
        String groupName = context.getJobDetail().getKey().getGroup();
        log.info("Executing Job: " + jobName + " in group: " + groupName);

        try {
            emailService.sendEmail("goviedo.sevenit@gmail.com","goviedo@sb.cl" , "EJALEEEEE!", "Viendo mientras veo una Western", "");
        } catch (MessagingException e) {
            log.error("Imposible enviar el mail: {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
        log.info("Job Ejemplo {} completed at {}", context.getJobDetail().getKey().getName(), new Date());
    }
}
