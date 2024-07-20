package cl.goviedo.emails.grupovias.jobs;

import cl.goviedo.emails.grupovias.bo.GrupoViasBO;
import cl.goviedo.emails.grupovias.services.correo.GoogleJavaMailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class EmailGrupoViasJob extends QuartzJobBean {

    private GoogleJavaMailService emailService;

    @Value("${email.from}")
    private String from;

    @Value("${email.to}")
    private String to;

    @Value("${email.cc}")
    private String listOfCCRecipients;

    @Value("${email.urgencia}")
    private String urgencia;

    private String subject;

    private String body;

    public EmailGrupoViasJob(GoogleJavaMailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException  {
        GrupoViasBO bo = new GrupoViasBO(from, urgencia);
        subject = bo.getSubject();
        body = bo.getBody();

        log.info("Job {} fired at {}", context.getJobDetail().getKey().getName(), new Date());

        try {

            emailService.sendEmail(from, to, subject, body, listOfCCRecipients);

            log.info("Job {} completed at {}", context.getJobDetail().getKey().getName(), new Date());
        } catch (MessagingException e) {
            log.error("EmailGrupoViasJob: Error al enviar el EMAIL {}",e.getMessage(),e);
            JobExecutionException jobException = new JobExecutionException(e);
            jobException.setRefireImmediately(true); // Decide whether to refire
            throw  new JobExecutionException("EmailGrupoViasJob con problemas: ",e);
        }
    }
}
