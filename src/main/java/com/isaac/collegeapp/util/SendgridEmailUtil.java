package com.isaac.collegeapp.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.model.TokenDAO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;


// The purpose of this class is to send 6 digit verification tokens to phone numbers for account 2FA
@Component
public class SendgridEmailUtil {

    @Autowired
    Environment env;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();


    TwilioRestClient client;



    public void generateAndSendEmail(String dataToSend, ArrayList<String> emailList, String subject) throws IOException {


        // Will loop thru and send for each email in the list
        for(String email: emailList){

            System.out.println("Sending email with sendgrid... ");
            Email from = new Email("admin@techvvs.io");
            Email to = new Email(email);
            Content content = new Content("text/plain", dataToSend);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(env.getProperty("sendgrid.api.key"));

            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {
                System.out.println("Caught Exception sending email: "+ex.getMessage());
                throw ex;
            }



        }

    }









    // todo: enforce country code
    public String sendValidationText(SystemUserDAO systemUserDAO, String token) {

        String message = "Validate your techvvs account with token: "+token;

        send(systemUserDAO.getPhone(), message);


        return "success";
    }

    public void send(String to, String message) {

        // SID and Auth Token respectively
        client = new TwilioRestClient.Builder(env.getProperty("twilio.api.username"), env.getProperty("twilio.api.password")).build();

        try{
            // to and from respectively
            new MessageCreator(
                    new PhoneNumber(to),
                    new PhoneNumber("+1 866 720 6310"),
                    message
            ).create(client);

        } catch(Exception ex){
            System.out.println("Caught Exception sending twilio sms: "+ex.getMessage() );
        }

    }

    public String createAndSendNewPhoneToken(SystemUserDAO systemUserDAO){
        // save a token value to a username and then send a text message
        String tokenval = String.valueOf(secureRandom.nextInt(1000000));
        String result = "";
        try {
            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.setUsermetadata(systemUserDAO.getEmail());
            tokenDAO.setToken(tokenval);
            tokenDAO.setTokenused(0);
            tokenDAO.setCreatetimestamp(LocalDateTime.now());
            tokenDAO.setUpdatedtimestamp(LocalDateTime.now());
            tokenRepo.save(tokenDAO);

        } catch(Exception ex){
            System.out.println("error inserting token into database");
        } finally{
            // only send the text message after everything else went smoothly
            // todo : check result of this
            result = sendValidationText(systemUserDAO, tokenval);
            System.out.println("Send Validation with result: "+result);
        }


        return "success";


    }
}
