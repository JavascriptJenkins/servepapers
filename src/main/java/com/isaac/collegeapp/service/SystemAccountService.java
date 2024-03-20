package com.isaac.collegeapp.service;

import com.google.common.base.CharMatcher;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.security.JwtTokenProvider;
import com.isaac.collegeapp.security.Role;
import com.isaac.collegeapp.util.SendgridEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class SystemAccountService {

    @Autowired
    Environment env;

    @Autowired
    SystemUserRepo systemUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    SendgridEmailUtil emailManager;

    SecureRandom secureRandom = new SecureRandom();


    public SystemUserDAO buildSystemUserFromContactForm(SystemUserDAO systemUserDAO) {
        Role[] array = new Role[1];
        array[0] = Role.ROLE_CLIENT;
        systemUserDAO.setRoles(array); // add write access

        if ("dev1".equals(env.getProperty("spring.profiles.active"))) {
            System.out.println("setting isuseractive=1 because we are in dev");
            systemUserDAO.setIsuseractive(1); // set this to 1 when email token is created
        } else {
            systemUserDAO.setIsuseractive(0); // set this to 1 when email token is created
        }

        // systemUserDAO.setId(0);
        systemUserDAO.setCreatetimestamp(LocalDateTime.now());
        systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());


        // we dont care if the user already exists or not, because we want as many inquiries as poassible



        // not checking for errors here yet
       String errorResult = validateCreateSystemUserForPipeline(systemUserDAO);


            try {

                systemUserDAO.setPassword(passwordEncoder.encode(systemUserDAO.getPassword()));

                systemUserRepo.save(systemUserDAO);

                TokenVO tokenVO = new TokenVO();

                // not doing anything with tokens for promo users right now
                tokenVO.setEmail(systemUserDAO.getEmail());
                tokenVO.setTokenused(0);

                // send user an email link to validate account
                if ("dev1".equals(env.getProperty("spring.profiles.active"))) {
                    System.out.println("SKIPPING SENDING EMAIL BECAUSE WE ARE IN DEV");
                } else {
                    sendCustomerPipelineWelcomeEmail(tokenVO);
                    sendAdminEmailInquiery(systemUserDAO);
                }

            } catch (Exception ex) {
                System.out.println("TechVVS System Error in createSystemUser: " + ex.getMessage());
                return systemUserDAO;
            }


            return systemUserDAO;
      //  }

    }

    String sendPromotionalEmailForCustomerPipeline(SystemUserDAO systemUserDAO){

        if(systemUserDAO.getPhone() != null){
            String theDigits = CharMatcher.inRange('0', '9').retainFrom(systemUserDAO.getPhone());
            systemUserDAO.setPhone(theDigits);
        }

        if(systemUserDAO.getPhone().length() > 11
                || systemUserDAO.getPhone().length() < 10
                || systemUserDAO.getPhone().contains("-")
                || systemUserDAO.getPhone().contains(".")
        ){
            return "enter 10 or 11 digit phone number (special characters are ignored).  ex. 18884445555";
        } else {
            systemUserDAO.setPhone(systemUserDAO.getPhone().trim());
            systemUserDAO.setPhone(systemUserDAO.getPhone().replaceAll(" ",""));
        }

        if(systemUserDAO.getPhone().length() == 10){
            systemUserDAO.setPhone("1"+systemUserDAO.getPhone()); // add usa country code if phone number is 10 digits
        }

        if(systemUserDAO.getEmail().length() < 6
                || systemUserDAO.getEmail().length() > 200
                || !systemUserDAO.getEmail().contains("@")
                || !systemUserDAO.getEmail().contains(".com")
            // todo: write method here to make sure there is text between @ and .com in the string

        ){
            return "email must be between 6-200 characters and contain @ and .com";
        } else {
            systemUserDAO.setEmail(systemUserDAO.getEmail().trim());
            systemUserDAO.setEmail(systemUserDAO.getEmail().replaceAll(" ",""));
        }


        if( systemUserDAO.getPassword().length() > 200
                || systemUserDAO.getPassword().length() < 8 ){
            return "password must be between 8-200 characters";
        }
        return "success";
    }



    void sendCustomerPipelineWelcomeEmail(TokenVO tokenVO){

        if(tokenVO.getEmail() != null &&
                tokenVO.getEmail().contains("@")){

            TokenVO newToken = new TokenVO();
            //generate token and send email
            newToken.setTokenused(0);
            newToken.setUsermetadata(tokenVO.getEmail());
            newToken.setEmail(tokenVO.getEmail());
            newToken.setToken(String.valueOf(secureRandom.nextInt(1000000))); // todo: make this a phone token sent over text message
            newToken.setUpdatedtimestamp(LocalDateTime.now());
            newToken.setCreatetimestamp(LocalDateTime.now());


            try{
                // not doing any token things with promo users.  just sending them an info email
//                ArrayList<String> list = new ArrayList<String>(1);
//                list.add(newToken.getEmail());
//                StringBuilder sb = new StringBuilder();
//
//                List<Role> roles = new ArrayList<>(1);
//                roles.add(Role.ROLE_CLIENT);
//                String emailtoken = jwtTokenProvider.createTokenForEmailValidation(tokenVO.getEmail(), roles);
                StringBuilder sb = new StringBuilder();
                ArrayList<String> list = new ArrayList<String>(1);
                list.add(newToken.getEmail());


                sb.append("Thank you for inquiring for technical services at TechVVS.  We will contact you about your inquiry in the next 1-3 business days. ");
                sb.append("https://techvvs.io");
               // sb.append("Verify your new account at http://localhost:8080/login/verify?customJwtParameter="+emailtoken);

                emailManager.generateAndSendEmail(sb.toString(), list, "We have received your inquiry about TechVVS services");
            } catch (Exception ex){
                System.out.println("error sending email");
                System.out.println(ex.getMessage());

            }

        }

    }

    void sendAdminEmailInquiery(SystemUserDAO systemUserDAO){

            try{
                StringBuilder sb = new StringBuilder();
                ArrayList<String> list = new ArrayList<String>(1);
                list.add("admin@techvvs.io");

                sb.append("Client name: "+systemUserDAO.getName());
                sb.append("Client email: "+systemUserDAO.getEmail());
                sb.append("Client project: "+systemUserDAO.getProject());
                sb.append("<br>");
                sb.append("https://techvvs.io");
                // sb.append("Verify your new account at http://localhost:8080/login/verify?customJwtParameter="+emailtoken);

                emailManager.generateAndSendEmail(sb.toString(), list, "New inquiry about TechVVS services");
            } catch (Exception ex){
                System.out.println("error sending email");
                System.out.println(ex.getMessage());

            }

        }




    String validateCreateSystemUserForPipeline(SystemUserDAO systemUserDAO){

        //
        systemUserDAO.setPhone("18887776666");
        systemUserDAO.setPassword("password");

        if(systemUserDAO.getPhone() != null){
            String theDigits = CharMatcher.inRange('0', '9').retainFrom(systemUserDAO.getPhone());
            systemUserDAO.setPhone(theDigits);
        }

        if(systemUserDAO.getPhone().length() > 11
                || systemUserDAO.getPhone().length() < 10
                || systemUserDAO.getPhone().contains("-")
                || systemUserDAO.getPhone().contains(".")
        ){
            return "enter 10 or 11 digit phone number (special characters are ignored).  ex. 18884445555";
        } else {
            systemUserDAO.setPhone(systemUserDAO.getPhone().trim());
            systemUserDAO.setPhone(systemUserDAO.getPhone().replaceAll(" ",""));
        }

        if(systemUserDAO.getPhone().length() == 10){
            systemUserDAO.setPhone("1"+systemUserDAO.getPhone()); // add usa country code if phone number is 10 digits
        }

        if(systemUserDAO.getEmail().length() < 6
                || systemUserDAO.getEmail().length() > 200
                || !systemUserDAO.getEmail().contains("@")
                || !systemUserDAO.getEmail().contains(".com")
            // todo: write method here to make sure there is text between @ and .com in the string

        ){
            return "email must be between 6-200 characters and contain @ and .com";
        } else {
            systemUserDAO.setEmail(systemUserDAO.getEmail().trim());
            systemUserDAO.setEmail(systemUserDAO.getEmail().replaceAll(" ",""));
        }


        if( systemUserDAO.getPassword().length() > 200
                || systemUserDAO.getPassword().length() < 8 ){
            return "password must be between 8-200 characters";
        }
        return "success";
    }




}
