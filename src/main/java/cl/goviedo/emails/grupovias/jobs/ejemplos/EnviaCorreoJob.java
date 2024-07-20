package cl.goviedo.emails.grupovias.jobs.ejemplos;

import cl.goviedo.emails.grupovias.services.correo.GoogleJavaMailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class EnviaCorreoJob extends QuartzJobBean {

    @Autowired
    private GoogleJavaMailService emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            emailService.sendEmail("goviedo.sevenit@gmail.com","goviedo@sb.cl" , "EJALEEEEE!", "Viendo mientras veo una Western", "");
            log.info("Job Ejemplo {} completed at {}", context.getJobDetail().getKey().getName(), new Date());
        } catch (MessagingException e) {
            log.error("EmailGrupoViasJob: Error al enviar el EMAIL {}",e.getMessage(),e);
            JobExecutionException jobException = new JobExecutionException(e);
            jobException.setRefireImmediately(true);
            throw  new JobExecutionException("EmailGrupoViasJob con problemas: ",e);
        }
    }
}
