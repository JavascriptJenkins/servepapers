package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.model.TokenDAO;
import com.isaac.collegeapp.security.JwtTokenProvider;
import com.isaac.collegeapp.security.Role;
import com.isaac.collegeapp.security.Token;
import com.isaac.collegeapp.security.UserService;
import com.isaac.collegeapp.service.StudentService;

import com.isaac.collegeapp.util.SendgridEmailUtil;
import com.isaac.collegeapp.util.TwilioTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    JwtTokenProvider jwtTokenProvider;


    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    TwilioTextUtil textMagicUtil;




    @Autowired
    SystemUserRepo systemUserRepo;

    @Autowired
    Environment env;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();

    @Autowired
    SendgridEmailUtil emailManager;


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
    @PostMapping("/logout")
    String logout(Model model, @RequestParam("customJwtParameter") String token,@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO){


        model.addAttribute("customJwtParameter","");
        SecurityContextHolder.getContext().setAuthentication(null); // clear the internal auth
        SecurityContextHolder.clearContext();
        model.addAttribute("systemuser", new SystemUserDAO());
        return "auth.html";
    }

    //error page displayed when nobody is logged in
    @GetMapping("/createaccount")
    String viewCreateAccount(Model model){

        model.addAttribute("systemuser", new SystemUserDAO());
        return "newaccount.html";
    }

    // display the page for typing in the token when user clicks link on phone
//    @GetMapping("/verifylinkphonetoken")
//    String verifylinkphonetoken(Model model, @RequestParam("email") String email){
//
//        TokenDAO token = new TokenDAO();
//        token.setUsermetadata(email);
//        model.addAttribute("token", token);
//        return "verifylinkphonetoken.html";
//    }

    // display the page for typing in the token
    @GetMapping("/verifyphonetoken")
    String verifyphonetoken(Model model, @RequestParam("email") String email){

        TokenDAO token = new TokenDAO();
        token.setUsermetadata(email);
        model.addAttribute("tknfromcontroller", token);
        return "verifyphonetoken.html";
    }

    // display the page for typing in the token
    @PostMapping("/verifyphonetoken")
    String postverifyphonetoken(Model model, @ModelAttribute( "tknfromcontroller" ) TokenDAO tokenDAO){

        if(tokenDAO != null && tokenDAO.getUsermetadata() != null && tokenDAO.getToken() != null){

            if(tokenDAO.getToken().length() < 5 ){
                model.addAttribute("errorMessage", "Token must be at least characters. ");
                model.addAttribute("tknfromcontroller", tokenDAO);
                return "authEnterPhoneToken.html";
            }


            Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(tokenDAO.getUsermetadata())); // see if user exists

            if(tokenDAO.getPassword() != null && tokenDAO.getPassword().length() > 1 ){


                if(existingUser.isPresent() && passwordEncoder.matches(tokenDAO.getPassword(), existingUser.get().getPassword())){
                    // this means passwords match
                    System.out.println("passwords match");

                } else {
                    System.out.println("passwords do not match");
                    model.addAttribute("tknfromcontroller", tokenDAO);
                    model.addAttribute("errorMessage","Unable to login. ");
                    return "authEnterPhoneToken.html";
                }
            } else {
                System.out.println("passwords not in correct format");
                model.addAttribute("tknfromcontroller", tokenDAO);
                model.addAttribute("errorMessage","password is blank ");
                return "authEnterPhoneToken.html";
            }




            TokenDAO latest;
            List<TokenDAO> tokenlist = tokenRepo.findTop10ByUsermetadataOrderByCreatetimestampDesc(tokenDAO.getUsermetadata());
            if(tokenlist != null && tokenlist.size() > 0){



                latest = tokenlist.get(0);
                // make sure token matches the one passed from controller
                if(!latest.getToken().equals(tokenDAO.getToken())){
                    // the tokens don't match then we send them back
                    model.addAttribute("errorMessage", "Token does not match.  Make sure you are entering the correct value or try logging in again. "); // todo: add a login link here
                    model.addAttribute("tknfromcontroller", tokenDAO);
                    return "authEnterPhoneToken.html";
                }




                if(latest.getTokenused() == 1){
                    // if token is used send them back
                    model.addAttribute("errorMessage", "Try logging in again so a new token will be sent. "); // todo: add a login link here
                    model.addAttribute("tknfromcontroller", tokenDAO);
                    return "authEnterPhoneToken.html";

                } else if(latest.getTokenused() == 0){


                    latest.setUpdatedtimestamp(LocalDateTime.now());
                    latest.setTokenused(1);
                    // have them validate existing token
                    System.out.println("User has valid token, setting it to used now. ");
                    tokenRepo.save(latest);


                    // note - user will be active if the email link has been clicked
                    // insert jwt token minting here
                    try{

                        if(existingUser.isPresent() && existingUser.get().getIsuseractive() == 0){
                            model.addAttribute("tknfromcontroller", tokenDAO);
                            System.out.println("User exists but is not active.  User needs to activate email. ");
                            model.addAttribute("errorMessage","Unable to login. If you have created an account, check your email (and spam) for account activation link. ");
                          //  return "authVerifySuccess.html";
                            return "authEnterPhoneToken.html";
                        } else if(existingUser.isEmpty()){
                            model.addAttribute("tknfromcontroller", tokenDAO);
                            model.addAttribute("errorMessage","Unable to login. If you have created an account, check your email (and spam) for account activation link.  ");
                            //return "authVerifySuccess.html";
                            return "authEnterPhoneToken.html";
                        }



                        if(existingUser.isPresent()){
                            String token = userService.signin(
                                    existingUser.get().getEmail(),
                                    tokenDAO.getPassword()); // pass in plaintext password from server

                            Token token1;
                            if(token != null){
                                System.out.println("SIGN-IN TOKEN GENERATED!!! ");
                                token1 = new Token();
                                token1.setToken(token);
                                model.addAttribute("customJwtParameter",token1.getToken());

                            } else {
                                System.out.println("TOKEN IS NULL THIS IS BAD BRAH! ");
                            }
                        }

                    } catch(Exception ex){
                        model.addAttribute("errorMessage","System Error");
                        System.out.println("TechVVS System Error in login: "+ex.getMessage());
                        model.addAttribute("tknfromcontroller", tokenDAO);
                       // return "auth.html"; // return early with error
                        return "authEnterPhoneToken.html";
                    }



                    model.addAttribute("successMessage", "Phone token verified. ");
                    model.addAttribute("tknfromcontroller", tokenDAO);
                    return "index.html";

                }
            }






        } else {
            model.addAttribute("errorMessage", "fill out required fields");
        }

//        return "index.html";


        model.addAttribute("tknfromcontroller", new TokenDAO());
        return "authEnterPhoneToken.html";
    }

    @GetMapping("/verify")
    String viewAuthVerify(@RequestParam("customJwtParameter") String token, Model model){

        String email = jwtTokenProvider.getTokenSubject(token);

        // check if user exists
        SystemUserDAO systemUserDAO = systemUserRepo.findByEmail(email);

        if(systemUserDAO != null && email != null){

            // update system user object
            systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());
            systemUserDAO.setIsuseractive(1);

            try{
                systemUserRepo.save(systemUserDAO);
            } catch (Exception ex){
                model.addAttribute("errorMessage", "System Error.  Try again or contact support: info@techvvs.io");
                System.out.println("Error in viewAuthVerify: "+ex.getMessage());
                return "authVerifySuccess.html";
            }

            // add data for page display
            model.addAttribute("successMessage", "Success activating account with email: "+email);
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

        if("dev1".equals(env.getProperty("spring.profiles.active"))){
            System.out.println("setting isuseractive=1 because we are in dev");
            systemUserDAO.setIsuseractive(1); // set this to 1 when email token is created
        } else {
            systemUserDAO.setIsuseractive(0); // set this to 1 when email token is created
        }

       // systemUserDAO.setId(0);
        systemUserDAO.setCreatetimestamp(LocalDateTime.now());
        systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());

        String errorResult = validateCreateSystemUser(systemUserDAO);

        Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail())); // see if user exists


        // Validation on student name
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else if(existingUser.isPresent()){
            model.addAttribute("errorMessage","Cannot create account. ");
        } else{



            try{

                systemUserDAO.setPassword(passwordEncoder.encode(systemUserDAO.getPassword()));

                systemUserRepo.save(systemUserDAO);

                TokenVO tokenVO = new TokenVO();

                tokenVO.setEmail(systemUserDAO.getEmail());
                tokenVO.setTokenused(0);

                // send user an email link to validate account
                if("dev1".equals(env.getProperty("spring.profiles.active"))){
                    System.out.println("SKIPPING SENDING EMAIL BECAUSE WE ARE IN DEV");
                } else {
                    sendValidateEmailToken(tokenVO);
                }

                model.addAttribute("successMessage","Check your email (and spam folder) to activate account: "+systemUserDAO.getEmail());
                return "accountcreated.html";

            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error in createSystemUser: "+ex.getMessage());
                return "newaccount.html"; // return early with error
            }



        }

        return "newaccount.html";
    }
// todo: remove passing the password from this html page (auth.html)
    @PostMapping ("/systemuser")
    String login(@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO, Model model, HttpServletResponse response){


        Optional<SystemUserDAO> userfromdb = Optional.empty();
        String errorResult = validateLoginInfo(systemUserDAO);

        // todo: add a feature to make sure login pages are not abused with ddos (maybe nginx setting)
        // if the email is valid format, do a lookup to see if there is actually a user with this email in the system
        if("success".equals(errorResult)){
            userfromdb = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail()));



            // if there is no user with this email reject them
            if(userfromdb.isEmpty()){
                System.out.println("User tried to login with an email that does not exist. ");
                errorResult = "Unable to login.  Check your email for validation link. ";
                model.addAttribute("errorMessage",errorResult);
                return "auth.html"; // return early with error
            }

            // if user exists but is not active, tell them to check their email and validate
            if(userfromdb.get().getIsuseractive() == 0){
                System.out.println("User exists but is not active. ");
                errorResult = "Unable to login. Check your email for validation link. ";
                model.addAttribute("errorMessage",errorResult);
                return "auth.html"; // return early with error
            }




        }



        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
            return "auth.html"; // return early with error
        } else {


            // todo: check if one hour has passed on the token
            // pull token from database and see if 1 hour has passed and if the token is unused
            try{
                List<TokenDAO> tokenlist = tokenRepo.findTop10ByUsermetadataOrderByCreatetimestampAsc(systemUserDAO.getEmail());
                System.out.println("Size of Token list. "+String.valueOf(tokenlist.size()));
                if(tokenlist != null && tokenlist.size() > 0){
                    TokenDAO latest = tokenlist.get(0);
                    if(latest.getTokenused() == 1){
                        System.out.println("User has token that is already used. Making a new one now. ");
                        //send a new token
                        textMagicUtil.createAndSendNewPhoneToken(userfromdb.get());

                    } else if(latest.getTokenused() == 0){
                        // have them validate existing token
                        System.out.println("User has valid phone token not expired yet. ");
                    }
                }

                // upon account creation we are putting a token in the database

                // if the user has no token yet we make one and text it here
                if(tokenlist != null && tokenlist.isEmpty()){


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
                        System.out.println("new token saved successfully for user with no existing token. ");

                    } catch(Exception ex){
                        System.out.println("error inserting token into database");
                    } finally{
                        System.out.println("sending out validation text");
                        // only send the text message after everything else went smoothly
                        // todo : check result of this
                        result = textMagicUtil.sendValidationText(userfromdb.get(), tokenval);
                    }


                    // If we have 1 token in the database for a user, and that token is NOT used, do this
                    if(tokenlist != null && tokenlist.size() == 1){
                        TokenDAO latest = tokenlist.get(0);
                        if(latest.getTokenused() == 1){
                            System.out.println("User has token that is already used. Making a new one now. ");
                            //send a new token
                            textMagicUtil.createAndSendNewPhoneToken(userfromdb.get());

                        } else if(latest.getTokenused() == 0){
                            // have them validate existing token
                            System.out.println("User has valid phone token not expired yet. ");
                        }
                    }


                }





            } catch(Exception ex){
                System.out.println("token issue: " +ex.getMessage());
            }

            // if token is expired or used, then send a new text message and insert a new token



            TokenDAO tokenDAO = new TokenDAO();
            tokenDAO.setUsermetadata(systemUserDAO.getEmail());
  //          model.addAttribute("successMessage","You are logged in: "+systemUserDAO.getEmail());
            model.addAttribute("systemuser",systemUserDAO);
            model.addAttribute("tknfromcontroller",tokenDAO);
        }

      //  return "index.html";
        return "authEnterPhoneToken.html";
    }

    @GetMapping ("/viewresetpass")
    String viewresetpass(@RequestParam("customJwtParameter") String token, Model model, HttpServletResponse response){

        String email = jwtTokenProvider.getTokenSubject(token);

        SystemUserDAO systemUserDAO = new SystemUserDAO();
        systemUserDAO.setEmail(email);
        model.addAttribute("systemuser",systemUserDAO);

        return "authActuallyResetPassword.html";
    }

    @GetMapping ("/resetpassword")
    String resetpasswordFromEmailLink(Model model, HttpServletResponse response){

        model.addAttribute("systemuser",new SystemUserDAO());
        return "authResetPassword.html";
    }

    @PostMapping ("/actuallyresetpassword")
    String actuallyresetpassword(@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO, Model model, HttpServletResponse response){



        String errorResult = validateActuallyResetPasswordInfo(systemUserDAO);

        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {

            try{
                Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail())); // see if user exists

                if(existingUser.isPresent() && existingUser.get().getIsuseractive() == 0){
                    model.addAttribute("errorMessage","Unable to process request. ");
                    return "authVerifySuccess.html";
                } else if(existingUser.isPresent() && existingUser.get().getIsuseractive() == 1){

                        existingUser.get().setPassword(passwordEncoder.encode(systemUserDAO.getPassword())); // set new password here
                        systemUserRepo.save(existingUser.get());
                }

            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error: "+ex.getMessage());
                return "authResetPassword.html"; // return early with error
            }
            model.addAttribute("successMessage","Password change success! ");
        }

        return "authVerifySuccess.html";
    }

    @PostMapping ("/resetpasswordbyemail")
    String resetpasswordbyemail(@ModelAttribute( "systemuser" ) SystemUserDAO systemUserDAO, Model model, HttpServletResponse response){


        String errorResult = "success";

        if(systemUserDAO.getEmail().length() < 6
                || systemUserDAO.getEmail().length() > 200
                || !systemUserDAO.getEmail().contains("@")
                || !systemUserDAO.getEmail().contains(".com")

        ){
            errorResult = "email must be between 6-200 characters and contain @ and .com";
        } else {
            systemUserDAO.setEmail(systemUserDAO.getEmail().trim());
            systemUserDAO.setEmail(systemUserDAO.getEmail().replaceAll(" ",""));
        }

        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {

            try{
                Optional<SystemUserDAO> existingUser = Optional.ofNullable(systemUserRepo.findByEmail(systemUserDAO.getEmail())); // see if user exists

                if(existingUser.isPresent() && existingUser.get().getIsuseractive() == 0){
                    model.addAttribute("successMessage","If email exists in techvvs system, a link was sent to that email to reset password.  Check spam folder. ");
                    return "authVerifySuccess.html";
                } else if(existingUser.isPresent() && existingUser.get().getIsuseractive() == 1){

                    try{
                        ArrayList<String> list = new ArrayList<String>(1);
                        list.add(existingUser.get().getEmail());
                        StringBuilder sb = new StringBuilder();

                        List<Role> roles = new ArrayList<>(1);
                        roles.add(Role.ROLE_CLIENT);
                        String emailtoken = jwtTokenProvider.createTokenForEmailValidation(existingUser.get().getEmail(), roles);

                        sb.append("Change password for your techvvs account at https://servepapers.techvvs.io/login/viewresetpass?customJwtParameter="+emailtoken);

                        emailManager.generateAndSendEmail(sb.toString(), list, "Change password request TechVVS ServePapers account");
                    } catch (Exception ex){
                        System.out.println("error sending email");
                        System.out.println(ex.getMessage());

                    }

                }



            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error: "+ex.getMessage());
                return "authResetPassword.html"; // return early with error
            }
            model.addAttribute("successMessage","If email exists in techvvs system, a link was sent to that email to reset password.  Check spam folder. ");
        }

        return "auth.html";
    }

    String validateActuallyResetPasswordInfo(SystemUserDAO systemUserDAO){

        if(systemUserDAO.getEmail() == null || systemUserDAO.getPassword() == null || systemUserDAO.getEmail().isBlank()|| systemUserDAO.getPassword().isBlank()){
            return "either email or password is blank";
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


    String validateLoginInfo(SystemUserDAO systemUserDAO){

        // note - not validating password in here becasue it's not needed until phonetoken/password page
        if(systemUserDAO.getEmail() == null || systemUserDAO.getEmail().isBlank()){
            return "email is blank";
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

        // note - not validating password in here becasue it's not needed until phonetoken/password page

//        if( systemUserDAO.getPassword().length() > 200
//                || systemUserDAO.getPassword().length() < 8 ){
//            return "password must be between 8-200 characters";
//        }
        return "success";
    }


    String validateCreateSystemUser(SystemUserDAO systemUserDAO){

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



    void sendValidateEmailToken(TokenVO tokenVO){

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
                ArrayList<String> list = new ArrayList<String>(1);
                list.add(newToken.getEmail());
                StringBuilder sb = new StringBuilder();

                List<Role> roles = new ArrayList<>(1);
                roles.add(Role.ROLE_CLIENT);
                String emailtoken = jwtTokenProvider.createTokenForEmailValidation(tokenVO.getEmail(), roles);

                sb.append("Verify your new account at https://servepapers.techvvs.io/login/verify?customJwtParameter="+emailtoken);

                emailManager.generateAndSendEmail(sb.toString(), list, "Validate email for new TechVVS ServePapers account");
            } catch (Exception ex){
                System.out.println("error sending email");
                System.out.println(ex.getMessage());

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









//    @PostMapping("/requesttoken")
//    String requesttoken(@ModelAttribute( "canceltrain" ) TokenVO tokenVO, Model model){
//
//
//        if(tokenVO.getEmail() != null &&
//                tokenVO.getEmail().contains("@")){
//
//
//            // todo: send cancel token
//            TokenVO newToken = new TokenVO();
//
//            //generate token and send email
//            newToken.setTokenused(0);
//            newToken.setUsermetadata(tokenVO.getEmail()); // todo: hash this email
//            newToken.setEmail(tokenVO.getEmail()); // todo: hash this email
//            newToken.setToken(String.valueOf(secureRandom.nextInt(1000000)));
//            newToken.setUpdatedtimestamp(LocalDateTime.now());
//            newToken.setCreatetimestamp(LocalDateTime.now());
//
//
//            //todo: send email before saving
//            try{
//                ArrayList<String> list = new ArrayList<String>(1);
//                list.add(newToken.getEmail());
//                StringBuilder sb = new StringBuilder();
//                sb.append("Here is your voting token: ");
//                sb.append(newToken.getToken());
//                sb.append(". ");
//                sb.append("Use it within 24 hours to cast votes on https://www.canceltrain.com");
//
//                emailManager.generateAndSendEmail(sb.toString(), list, "Voting token from Cancel Train!");
//            } catch (Exception ex){
//                model.addAttribute("errorMessage", "Error sending email!");
//                model.addAttribute("canceltrain", new CancelTrainVO());
//                return "token.html";
//
//            } finally{
//                tokenRepo.save(newToken);
//            }
//
//
//            model.addAttribute("successMessage", "Thank you for requesting a cancel token!  It will only be valid for 24 hours and one vote!");
//
//            model.addAttribute("canceltrain", new CancelTrainVO());
//
//
//        } else {
//            model.addAttribute("errorMessage", "Please enter a valid email address!");
//            model.addAttribute("canceltrain", new CancelTrainVO());
//        }
//
//        return "token.html";
//    }










}
