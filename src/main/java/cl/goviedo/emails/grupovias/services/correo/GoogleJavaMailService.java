package cl.goviedo.emails.grupovias.services.correo;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;


/**
 * https://mkyong.com/spring/spring-sending-e-mail-via-gmail-smtp-server-with-mailsender/
 */
@Service
public class GoogleJavaMailService {


    private JavaMailSender mailSender;

    @Autowired
    public GoogleJavaMailService(JavaMailSender javaMailSender) {
        this.mailSender =javaMailSender;
    }

    public void sendEmail(String from, String to, String subject, String body, String listCCRecipients) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        message.addRecipients(Message.RecipientType.CC,
                InternetAddress.parse(listCCRecipients));

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true indicates HTML

        mailSender.send(message);
    }
}
