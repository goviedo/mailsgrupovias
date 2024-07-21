package cl.goviedo.emails.grupovias.jobs;

import cl.goviedo.emails.grupovias.bo.CorreoGeneralBO;
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
public class EmailCompromisoUrgenciaJob extends QuartzJobBean {

    @Autowired
    private GoogleJavaMailService emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            String urgencia = context.getJobDetail().getJobDataMap().getString("urgencia");
            String compromiso = context.getJobDetail().getJobDataMap().getString("compromiso");
            String destinatario = context.getJobDetail().getJobDataMap().getString("destinatario");
            String listaDestinatarios = context.getJobDetail().getJobDataMap().getString("listaDestinatarios");

            String from = "goviedo.sevenit@gmail.com";
            String to = destinatario;
            String ccs = listaDestinatarios;

            CorreoGeneralBO correo = new CorreoGeneralBO(compromiso,urgencia);
            correo.setSubject("Compromisos adquiridos y esperando respuesta. Favor responder a la brevedad posible.");

            emailService.sendEmail(from, to, correo.getSubject(), correo.getBody(), ccs);

            log.info("Correo enviado!. Job {} completed at {}", context.getJobDetail().getKey().getName(), new Date());

        } catch (MessagingException e) {
            log.error("EmailCompromisoUrgenciaJob: Error al enviar el EMAIL {}. Refiring...",e.getMessage(),e);
            JobExecutionException jobException = new JobExecutionException(e);
            jobException.setRefireImmediately(true); // Decide whether to refire
            throw  new JobExecutionException("EmailCompromisoUrgenciaJob con problemas: ",e);
        }
    }
}
