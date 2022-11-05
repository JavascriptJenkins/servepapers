package com.isaac.collegeapp.email.impl;

import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.email.objects.UserResetEmail;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class EmailManagerImpl implements EmailManager {

    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage generateMailMessage;

    public void generateAndSendEmail(String dataToSend, ArrayList<String> emailList, String subject) throws AddressException, MessagingException {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);

        for(String email: emailList){
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }

        generateMailMessage.setSubject(subject);
        String emailBody = dataToSend;
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        // todo: change this password when going to prod
        transport.connect("smtp.gmail.com", "info@techvvs.io", "Support1$");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    @Override
    public String formatUserResetEmailToHTML(ArrayList<UserResetEmail> userRestEmails) {
        return null;
    }
//    String formatCraigslistObjectsToEmailHTML(ArrayList<ScraperObject> scraperObjects){
//
//        StringBuilder emailHTML = new StringBuilder()
//
//        for(ScraperObject scraperObject : scraperObjects){
//            emailHTML.append("<h2>" + scraperObject.getPostTitle() + "</h2>")
//            emailHTML.append("<h2>" + scraperObject.getPrice() + "</h2>")
//            emailHTML.append("<a href=\""+scraperObject.getUrl()+"\">" + "<h4>" + scraperObject.getUrl() + "</h4>" + "</a>")
//            emailHTML.append("<hr>") // adds a line under each result
//        }
//
//        return emailHTML.toString()
//    }




}
