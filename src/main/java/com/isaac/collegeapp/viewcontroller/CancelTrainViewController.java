package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.CancelTrainRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.service.StudentService;
import com.isaac.collegeapp.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/")
@Controller
public class CancelTrainViewController {

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
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();

    @Autowired
    EmailManager emailManager;


    //default home mapping
    @GetMapping
    String viewHomePage(Model model){


        List<CancelTrainVO> cancelTrainVOList = calculateProgressBars();

//        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in
        model.addAttribute("canceltrains", cancelTrainVOList);
        model.addAttribute("canceltrain", new CancelTrainVO());

//        model.addAttribute("canceltrainNewCancel", new CancelTrainVO());



//        model.addAttribute("students", studentService.getAllStudentData());
        return "index.html";
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
            newToken.setUpdatedtimestamp(java.time.LocalDateTime.now());
            newToken.setCreatetimestamp(java.time.LocalDateTime.now());


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
            cancelTrainVO.setUpdatedtimestamp(java.time.LocalDateTime.now());
            cancelTrainVO.setCreatetimestamp(java.time.LocalDateTime.now());

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

    @PostMapping ("/createStudent")
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
