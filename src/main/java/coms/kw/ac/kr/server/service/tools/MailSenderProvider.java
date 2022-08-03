package coms.kw.ac.kr.server.service.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailSenderProvider {
    private final JavaMailSender mailSender;
    private final String username;

    private static final Logger logger = LoggerFactory.getLogger(MailSenderProvider.class);

    public interface MailSender {
        public void sendMail(String receiver, String subject, String text);
    }

    @Autowired
    public MailSenderProvider(JavaMailSender mailSender, @Value("${spring.mail.username}") String username) {
        this.mailSender = mailSender;
        this.username = username;
    }

    @Bean
    public MailSender getMailSender() {
        return (String receiver, String subject, String text) -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                message.setFrom(username);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
                message.setSubject(subject);
                message.setContent(text, "text/plain");
                message.setSentDate(new Date());
                mailSender.send(message);
            } catch (Exception exception) {
                logger.error("Exception from MailSender.", exception);
            }
        };
    }

}