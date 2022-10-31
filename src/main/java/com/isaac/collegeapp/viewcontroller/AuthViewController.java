package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.constants.StaticRoles;
import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.CancelTrainRepo;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.service.StudentService;
import com.isaac.collegeapp.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/auth")
@Controller
public class AuthViewController {

    @Autowired
    StudentService studentService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    CancelTrainRepo cancelTrainRepo;

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


        //List<CancelTrainVO> cancelTrainVOList = calculateProgressBars();

//        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in
//        model.addAttribute("canceltrains", cancelTrainVOList);
//        model.addAttribute("canceltrain", new CancelTrainVO());

//        model.addAttribute("canceltrainNewCancel", new CancelTrainVO());



//        model.addAttribute("students", studentService.getAllStudentData());
        return "auth.html";
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
            systemUserDAO.setRoles(staticRoles.getREAD()+staticRoles.getWRITE()); // add write access

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

        String errorResult = validateCreateSystemUser(systemUserDAO);

        // Validation on student name
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {
            // This code block will execute if there are no errors in the data that was inputed by the client

            // step 1) create the new student and attach the success message

            // give the user read priveledges until account is activated
            systemUserDAO.setRoles(staticRoles.getREAD());
            systemUserDAO.setIsuseractive(0); // set this to 1 when email token is created
            systemUserDAO.setCreatetimestamp(LocalDateTime.now());
            systemUserDAO.setUpdatedtimestamp(LocalDateTime.now());

            try{
                systemUserRepo.save(systemUserDAO);

                TokenVO tokenVO = new TokenVO();

                tokenVO.setEmail(systemUserDAO.getEmail());
                tokenVO.setTokenused(0);

                // send user an email link to validate account
                sendValidateEmailToken(tokenVO);

            } catch(Exception ex){
                model.addAttribute("errorMessage","System Error");
                System.out.println("TechVVS System Error in createSystemUser: "+ex.getMessage());
                return "newStudent.html"; // return early with error
            }


            model.addAttribute("successMessage","Check your email to activate account: "+systemUserDAO.getEmail());

        }

        return "newStudent.html";
    }

    String validateCreateSystemUser(SystemUserDAO systemUserDAO){
        if(systemUserDAO.getUsername().length() > 100
                || systemUserDAO.getUsername().length() < 6
        ){
            return "username must be between 6-100 characters and contain @ and .com";
        }

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
            || systemUserDAO.getPassword().length() < 10 ){
            return "password must be between 10-200 characters";
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

                sb.append("Verify your new account at https://servepapers.techvvs.io/auth/verify&token="+newToken.getToken());

                emailManager.generateAndSendEmail(sb.toString(), list, "Voting token from Cancel Train!");
            } catch (Exception ex){
                System.out.println("error sending email");

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


    @PostMapping("/vote")
    String vote(@ModelAttribute( "canceltrain" ) CancelTrainVO cancelTrainVO, Model model){


        //check to see if the token is valid and has not expired
        TokenVO tokenVO = tokenRepo.findByToken(cancelTrainVO.getToken());
        if(tokenVO != null
        && (LocalDateTime.now().isBefore(tokenVO.getCreatetimestamp().plusHours(24))) // only allow tokens made within past 24 hours
                && tokenVO.getTokenused() < 1 // only allow 1 votes per token
        ){


            if(cancelTrainVO.getIncomingVote() ==0){
                Optional<CancelTrainVO> existing = cancelTrainRepo.findById(cancelTrainVO.getId());

                existing.get().setDownvotes(existing.get().getDownvotes() - 1);
                cancelTrainRepo.save(existing.get());

            } else if(cancelTrainVO.getIncomingVote() ==1){
                Optional<CancelTrainVO> existing = cancelTrainRepo.findById(cancelTrainVO.getId());
                existing.get().setUpvotes(existing.get().getUpvotes() + 1);
                cancelTrainRepo.save(existing.get());
            }


            // now we have to increment the token to isUsed == 1
            tokenVO.setTokenused(1);
            tokenRepo.save(tokenVO);
        } else {
            model.addAttribute("errorMessage", "Insert valid token below before voting. ");
            model.addAttribute("canceltrain", new CancelTrainVO());
            model.addAttribute("canceltrainNewCancel", new CancelTrainVO());

            model.addAttribute("canceltrains",calculateProgressBars());

            return "index.html";
        }





        model.addAttribute("canceltrain", new CancelTrainVO());
        model.addAttribute("canceltrainNewCancel", new CancelTrainVO());

        model.addAttribute("canceltrains",calculateProgressBars());


        return "redirect:/";
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

    @PostMapping("/newCancel")
    String newCancel(@ModelAttribute( "canceltrainNewCancel" ) CancelTrainVO cancelTrainVO, Model model){


        if(cancelTrainVO.getFname() != null
        && cancelTrainVO.getLname() != null
        && cancelTrainVO.getWhy() != null

        ){

            cancelTrainVO.setId(null);
            cancelTrainVO.setUpvotes(0);
            cancelTrainVO.setDownvotes(0);
            cancelTrainVO.setImageurl("");
            cancelTrainVO.setCancelstatus(0); // will be set to 1 to show in grid
            cancelTrainVO.setUpdatedtimestamp(LocalDateTime.now());
            cancelTrainVO.setCreatetimestamp(LocalDateTime.now());

            //todo:add validation so same people are not added twice on refresh or on purpose
            cancelTrainRepo.save(cancelTrainVO);

            //todo: add a link so people can be directed back home
            model.addAttribute("successMessage", "Thank you for submitting a new cancel candidate!");

            model.addAttribute("canceltrain", new CancelTrainVO());
            model.addAttribute("canceltrainNewCancel", new CancelTrainVO());
            model.addAttribute("canceltrains",cancelTrainRepo.findAll());

        } else {
            model.addAttribute("errorMessage", "Please fill out all data!");

            model.addAttribute("canceltrain", new CancelTrainVO());
            model.addAttribute("canceltrainNewCancel", new CancelTrainVO());
            model.addAttribute("canceltrains",calculateProgressBars());

        }




        return "requestcancel.html";
    }


    @GetMapping("/viewStudents")
    String viewStudents(Model model){

        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in


        model.addAttribute("students", studentService.getAllStudentData());
        return "viewStudents.html";
    }

    @GetMapping("/editStudent")
    String editStudent(Model model){
        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in


        model.addAttribute("student", new StudentDAO());
        model.addAttribute("students",studentService.getAllStudentData());
        return "editStudent.html";
    }

    @PostMapping("/submitEditStudent")
    String submitEditStudent(@ModelAttribute( "student" ) StudentDAO studentDAO, Model model){

        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in


        System.out.println(studentDAO);


        // now the edit works, just need to submit the edit to the database

        model.addAttribute("student", new StudentDAO());

        model.addAttribute("students",studentService.getAllStudentData());


        return "editStudent.html";
    }

    @GetMapping("/newStudent")
    String newStudent(Model model){
        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in


        model.addAttribute("student", new StudentDAO());
        model.addAttribute("students",studentService.getAllStudentData());
        return "newStudent.html";
    }

    @PostMapping ("/createUser")
    String createStudent(@ModelAttribute( "student" ) StudentDAO studentDAO, Model model){

        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in


        // Validation on student name
        if(studentDAO.getStudentName().length() > 60){
            model.addAttribute("errorMessage","Student Name must be shorter than 60 characters");
        } else {
            // This code block will execute if there are no errors in the data that was inputed by the client

            // step 1) create the new student and attach the success message
            String result = studentService.createStudent(studentDAO);
            model.addAttribute("successMessage",result);

            // step 2) fetch the list of students from the database and bind the list onto the page
            model.addAttribute("students",studentService.getAllStudentData());

        }

        return "newStudent.html";
    }


    List<CancelTrainVO> calculateProgressBars(){

        List<CancelTrainVO> cancelTrainVOList = cancelTrainRepo.findByOrderByUpvotesDesc();

        // set progress bar value
        for(CancelTrainVO cancelTrainVO : cancelTrainVOList){

            int progress = 0;
            if(cancelTrainVO.getDownvotes() >= cancelTrainVO.getUpvotes()){
                progress = 0;
            } else {
                progress = cancelTrainVO.getUpvotes() - Math.abs(cancelTrainVO.getDownvotes());
            }
            cancelTrainVO.setProgressbar(progress);
        }



        // this will sort based on progress bar value desc
        return cancelTrainVOList.stream()
                .sorted(Comparator.comparing(CancelTrainVO::getProgressbar).reversed())
                .collect(Collectors.toList());

    }


}
