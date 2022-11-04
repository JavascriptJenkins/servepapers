package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.constants.StaticRoles;
import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.security.Role;
import com.isaac.collegeapp.security.Token;
import com.isaac.collegeapp.security.UserService;
import com.isaac.collegeapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/login")
@Controller
public class AuthViewController {

    @Autowired
    StudentService studentService;


    @Autowired
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PasswordEncoder passwordEncoder;



    @Autowired
    HttpServletRequest httpServletRequest;



    @Autowired
    SystemUserRepo systemUserRepo;

    @Autowired
    StaticRoles staticRoles;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();

    @Autowired
    EmailManager emailManager;


    //default home mapping
    @GetMapping
    String viewAuthPage(Model model){

        model.addAttribute("systemuser", new SystemUserDAO());
        return "auth.html";
    }

    //error page displayed when nobody is logged in
    @GetMapping("/error")
    String showErrorPage(Model model){

        model.addAttribute("systemuser", new SystemUserDAO());
        return "error.html";
    }

    //error page displayed when nobody is logged in
    @GetMapping("/createaccount")
    String viewCreateAccount(Model model){

        model.addAttribute("systemuser", new SystemUserDAO());
        return "newaccount.html";
    }

    @GetMapping("/verify")
    String viewAuthVerify(@RequestParam("token") String token, Model model){


        TokenVO tokenVO = tokenRepo.findByToken(token);


        if(checkTokenExpire(tokenVO.getCreatetimestamp()) == 1){
            // if token is expired tell user to request another
            return "authRequestNewToken.html";
        }


        SystemUserDAO systemUserDAO = systemUserRepo.findByEmail(tokenVO.getEmail());




        if(tokenVO != null && tokenVO.getEmail() != null
                && systemUserDAO != null
                && tokenVO.getTokenused() == 0

        ){

            // update system user object
            systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());
            systemUserDAO.setIsuseractive(1);
           // systemUserDAO.setRoles(staticRoles.getREAD()+staticRoles.getWRITE()); // add write access
            Role[] array = new Role[1];
            array[0] = Role.ROLE_CLIENT;
            systemUserDAO.setRoles(array); // add write access

            try{
                systemUserRepo.save(systemUserDAO);
            } catch (Exception ex){
                model.addAttribute("errorMessage", "System Error.  Try again or contact support: support@techvvs.io");

                System.out.println("Error in viewAuthVerify: "+ex.getMessage());
                return "authVerifySuccess.html";
            }

            try{
                tokenVO.setTokenused(1);
                tokenVO.setUpdatedtimestamp(LocalDateTime.now());
                tokenRepo.save(tokenVO);
            } catch (Exception ex){
                model.addAttribute("errorMessage", "System Error.  Try again or contact support: support@techvvs.io");

                System.out.println("Error in viewAuthVerify: "+ex.getMessage());
                return "authVerifySuccess.html";
            }


            // add data for page display
            model.addAttribute("token", tokenVO.getEmail());
            model.addAttribute("successMessage", "Success activating account with email: "+tokenVO.getEmail());
        }
        return "authVerifySuccess.html";
    }

    int checkTokenExpire(LocalDateTime tokencreated){

        LocalDateTime oneHourFromTokenCreation = tokencreated.plusHours(1);
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(oneHourFromTokenCreation)){
            return 1; // this means one hour has passed and token is invalid
        }

        return 0;
    }

    @PostMapping ("/createSystemUser")
    String createSystemUser(@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO, Model model){

        //controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in

        Role[] array = new Role[1];
        array[0] = Role.ROLE_CLIENT;
        systemUserDAO.setRoles(array); // add write access

        systemUserDAO.setIsuseractive(0); // set this to 1 when email token is created
       // systemUserDAO.setId(0);
        systemUserDAO.setCreatetimestamp(LocalDateTime.now());
        systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());



        String errorResult = validateCreateSystemUser(systemUserDAO);

        Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail())); // see if user exists


        // Validation on student name
        if(!errorResult.equals("success") || existingUser.isPresent()){
            model.addAttribute("errorMessage",errorResult);
        } else {
            // This code block will execute if there are no errors in the data that was inputed by the client

            // step 1) create the new student and attach the success message

            // give the user read priveledges until account is activated
            //systemUserDAO.setRoles(staticRoles.getREAD());


            try{

                systemUserDAO.setPassword(passwordEncoder.encode(systemUserDAO.getPassword()));

                systemUserRepo.save(systemUserDAO);

                TokenVO tokenVO = new TokenVO();

                tokenVO.setEmail(systemUserDAO.getEmail());
                tokenVO.setTokenused(0);

                // send user an email link to validate account
                sendValidateEmailToken(tokenVO);
                model.addAttribute("successMessage","Check your email to activate account: "+systemUserDAO.getEmail());
                return "accountcreated.html";

            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error in createSystemUser: "+ex.getMessage());
                return "newaccount.html"; // return early with error
            }



        }

        return "newaccount.html";
    }

    @PostMapping ("/systemuser")
    String login(@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO, Model model, HttpServletResponse response){

        //controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in

        String errorResult = validateLoginInfo(systemUserDAO);

        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {

            try{
                Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail())); // see if user exists

                if(existingUser.isPresent()){
                    String token = userService.signin(
                            systemUserDAO.getEmail(),
                            systemUserDAO.getPassword());

                    Token token1;
                    if(token != null){
                        System.out.println("SIGN-IN TOKEN GENERATED!!! ");
                        token1 = new Token();
                        token1.setToken(token);
                        model.addAttribute("customJwtParameter",token1.getToken());
                        response.setHeader("Authorization: Bearer", mapper.writeValueAsString(token1));

                    } else {
                        System.out.println("TOKEN IS NULL THIS IS BAD BRAH! ");
                    }
                }

            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error in login: "+ex.getMessage());
                return "auth.html"; // return early with error
            }


            model.addAttribute("successMessage","You are logged in: "+systemUserDAO.getEmail());


        }

        return "index.html";
    }


    String validateLoginInfo(SystemUserDAO systemUserDAO){


        if(systemUserDAO.getEmail().length() < 6
                || systemUserDAO.getEmail().length() > 200
                || !systemUserDAO.getEmail().contains("@")
                || !systemUserDAO.getEmail().contains(".com")

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


    String validateCreateSystemUser(SystemUserDAO systemUserDAO){

        if(systemUserDAO.getPhone().length() > 11
                || systemUserDAO.getPhone().length() < 10
                || systemUserDAO.getPhone().contains("-")
                || systemUserDAO.getPhone().contains(".")
        ){
            return "enter 10 or 11 digit phone number with no spaces or symbols.  ex. 18884445555";
        } else {
            systemUserDAO.setPhone(systemUserDAO.getPhone().trim());
            systemUserDAO.setPhone(systemUserDAO.getPhone().replaceAll(" ",""));
        }

        if(systemUserDAO.getEmail().length() < 6
            || systemUserDAO.getEmail().length() > 200
            || !systemUserDAO.getEmail().contains("@")
            || !systemUserDAO.getEmail().contains(".com")

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

    void sendValidateEmailToken(TokenVO tokenVO){



        if(tokenVO.getEmail() != null &&
                tokenVO.getEmail().contains("@")){


            // todo: send cancel token
            TokenVO newToken = new TokenVO();

            //generate token and send email
            newToken.setTokenused(0);
            newToken.setUsermetadata(tokenVO.getEmail()); // todo: hash this email
            newToken.setEmail(tokenVO.getEmail()); // todo: hash this email
            newToken.setToken(String.valueOf(secureRandom.nextInt(100000)));
            newToken.setUpdatedtimestamp(LocalDateTime.now());
            newToken.setCreatetimestamp(LocalDateTime.now());


            //todo: send email before saving
            try{
                ArrayList<String> list = new ArrayList<String>(1);
                list.add(newToken.getEmail());
                StringBuilder sb = new StringBuilder();

                sb.append("Verify your new account at http://localhost:8080/auth/verify&token="+newToken.getToken());

                emailManager.generateAndSendEmail(sb.toString(), list, "Validate email for new TechVVS ServePapers account");
            } catch (Exception ex){
                System.out.println("error sending email");
                System.out.println(ex.getMessage());

            } finally{
                tokenRepo.save(newToken);
            }

        }

    }


    @GetMapping("/requestcancel")
    String requestcancel(Model model){

        model.addAttribute("canceltrainNewCancel", new CancelTrainVO());

//        model.addAttribute("students", studentService.getAllStudentData());
        return "requestcancel.html";
    }

    @GetMapping("/token")
    String token(Model model){

        model.addAttribute("canceltrain", new CancelTrainVO());

//        model.addAttribute("students", studentService.getAllStudentData());
        return "token.html";
    }









    @PostMapping("/requesttoken")
    String requesttoken(@ModelAttribute( "canceltrain" ) TokenVO tokenVO, Model model){


        if(tokenVO.getEmail() != null &&
                tokenVO.getEmail().contains("@")){


            // todo: send cancel token
            TokenVO newToken = new TokenVO();

            //generate token and send email
            newToken.setTokenused(0);
            newToken.setUsermetadata(tokenVO.getEmail()); // todo: hash this email
            newToken.setEmail(tokenVO.getEmail()); // todo: hash this email
            newToken.setToken(String.valueOf(secureRandom.nextInt(100000)));
            newToken.setUpdatedtimestamp(LocalDateTime.now());
            newToken.setCreatetimestamp(LocalDateTime.now());


            //todo: send email before saving
            try{
                ArrayList<String> list = new ArrayList<String>(1);
                list.add(newToken.getEmail());
                StringBuilder sb = new StringBuilder();
                sb.append("Here is your voting token: ");
                sb.append(newToken.getToken());
                sb.append(". ");
                sb.append("Use it within 24 hours to cast votes on https://www.canceltrain.com");

                emailManager.generateAndSendEmail(sb.toString(), list, "Voting token from Cancel Train!");
            } catch (Exception ex){
                model.addAttribute("errorMessage", "Error sending email!");
                model.addAttribute("canceltrain", new CancelTrainVO());
                return "token.html";

            } finally{
                tokenRepo.save(newToken);
            }


            model.addAttribute("successMessage", "Thank you for requesting a cancel token!  It will only be valid for 24 hours and one vote!");

            model.addAttribute("canceltrain", new CancelTrainVO());


        } else {
            model.addAttribute("errorMessage", "Please enter a valid email address!");
            model.addAttribute("canceltrain", new CancelTrainVO());
        }

        return "token.html";
    }










}
