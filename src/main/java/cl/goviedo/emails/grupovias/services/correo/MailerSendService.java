package cl.goviedo.emails.grupovias.services.correo;

import com.mailersend.sdk.*;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MailerSendService {

    public void sendEmail() {

        Email email = new Email();

        email.setFrom("Gonzalo Oviedo", "MS_91sJZR@trial-3z0vklooeeel7qrx.mlsender.net");
        email.addRecipient("Gol", "goviedo.sevenit@gmail.com");

        // you can also add multiple recipients by calling addRecipient again
        // email.addRecipient("name 2", "your@recipient2.com");


        email.setSubject("Email subject");

        email.setPlain("This is the text content");
        email.setHtml("This is the HTML content");

        MailerSend ms = new MailerSend();

        ms.setToken("mlsn.cdf123b1cfbc4d2739303a244255cdf3e96c739422162e0c681d9804ce54e3ba");

        try {

            MailerSendResponse response = ms.emails().send(email);
            log.info("{}", response.messageId);
        } catch (MailerSendException e) {
            log.error("No pude enviar el correo: {}", e);
        }
    }
}
