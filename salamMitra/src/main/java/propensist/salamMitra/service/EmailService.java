package propensist.salamMitra.service;

import propensist.salamMitra.model.EmailDetails;

public interface EmailService {
 
    String sendSimpleMail(EmailDetails details);

}
