package cl.goviedo.emails.grupovias.jobs;

import cl.goviedo.emails.grupovias.bo.GrupoViasBO;
import cl.goviedo.emails.grupovias.services.EmailService;
import cl.goviedo.emails.grupovias.services.GoogleJavaMailService;
import cl.goviedo.emails.grupovias.services.MailerSendService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {

    @Autowired
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


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {

            GrupoViasBO bo = new GrupoViasBO(from, urgencia);

            subject = bo.getSubject();
            body = bo.getBody();

            emailService.sendEmail(from, to, subject, body, listOfCCRecipients);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
