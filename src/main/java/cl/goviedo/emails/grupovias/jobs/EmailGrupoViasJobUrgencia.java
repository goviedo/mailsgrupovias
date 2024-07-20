package cl.goviedo.emails.grupovias.jobs;

import cl.goviedo.emails.grupovias.bo.GrupoViasBO;
import cl.goviedo.emails.grupovias.services.correo.GoogleJavaMailService;
import jakarta.mail.MessagingException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@NoArgsConstructor
public class EmailGrupoViasJobUrgencia extends QuartzJobBean {

    @Autowired
    private GoogleJavaMailService emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {

            String from = "goviedo.sevenit@gmail.com";
            String to = "goviedo@sb.cl";
            String ccs = "goviedo.sevenit@gmail.com";

            String urgencia = context.getJobDetail().getJobDataMap().getString("urgencia");

            GrupoViasBO bo = new GrupoViasBO(from,urgencia==null || urgencia.isEmpty()?"No llego la urgencia":urgencia);

            String subject = bo.getSubject();
            String body = bo.getBody();

            emailService.sendEmail(from, to, subject, body, ccs);

            log.info("Job {} completed at {}", context.getJobDetail().getKey().getName(), new Date());
        } catch (MessagingException e) {
            log.error("EmailGrupoViasJob: Error al enviar el EMAIL {}",e.getMessage(),e);
            JobExecutionException jobException = new JobExecutionException(e);
            jobException.setRefireImmediately(true); // Decide whether to refire
            throw  new JobExecutionException("EmailGrupoViasJob con problemas: ",e);
        }
    }
}
