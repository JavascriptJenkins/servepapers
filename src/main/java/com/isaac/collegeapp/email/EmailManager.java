package com.isaac.collegeapp.email;

import com.isaac.collegeapp.email.objects.UserResetEmail;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;

@Component
public interface EmailManager {

    void generateAndSendEmail(String dataToSend, ArrayList<String> emailList, String subject) throws AddressException, MessagingException;

    String formatUserResetEmailToHTML(ArrayList<UserResetEmail> userRestEmails);

}
