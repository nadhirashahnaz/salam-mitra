package propensist.salamMitra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import propensist.salamMitra.model.EmailDetails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired 
    private JavaMailSender javaMailSender;
 
    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
 
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMessageBody());
            mailMessage.setSubject(details.getSubject());
 
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";

        }catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
 
}