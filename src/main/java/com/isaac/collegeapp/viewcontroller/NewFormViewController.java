package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.constants.StaticRoles;
import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.jparepo.ProcessDataRepo;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.ProcessDataDAO;
import com.isaac.collegeapp.model.SystemUserDAO;
import com.isaac.collegeapp.modelnonpersist.FileVO;
import com.isaac.collegeapp.security.Role;
import com.isaac.collegeapp.security.Token;
import com.isaac.collegeapp.security.UserService;
import com.isaac.collegeapp.service.StudentService;
import com.isaac.collegeapp.util.TechvvsFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/newform")
@Controller
public class NewFormViewController {

    @Autowired
    StudentService studentService;


    @Autowired
    UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String UPLOAD_DIR = "./uploads/";

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    TechvvsFileHelper techvvsFileHelper;


    @Autowired
    SystemUserRepo systemUserRepo;

    @Autowired
    ProcessDataRepo processDataRepo;

    @Autowired
    StaticRoles staticRoles;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();

    @Autowired
    EmailManager emailManager;


    //default home mapping
    @GetMapping
    String viewNewForm(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO, Model model, @RequestParam("customJwtParameter") String customJwtParameter){

        System.out.println("customJwtParam on newform controller: "+customJwtParameter);

        ProcessDataDAO processDataDAOToBind;
        if(processDataDAO != null && processDataDAO.getId() != null){
            processDataDAOToBind = processDataDAO;
        } else {
            processDataDAOToBind = new ProcessDataDAO();
            processDataDAOToBind.setFilenumber(secureRandom.nextInt(10000000));
        }



        model.addAttribute("customJwtParameter", customJwtParameter);
        model.addAttribute("processdata", processDataDAOToBind);
        return "newform.html";
    }

    @GetMapping("/searchProcessData")
    String searchProcessData(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO, Model model, @RequestParam("customJwtParameter") String customJwtParameter){

        System.out.println("customJwtParam on newform controller: "+customJwtParameter);

        model.addAttribute("customJwtParameter", customJwtParameter);
        model.addAttribute("processdata", new ProcessDataDAO());
        model.addAttribute("processdatas", new ArrayList<ProcessDataDAO>(1));
        return "searchforms.html";
    }

    @PostMapping("/searchProcessData")
    String searchProcessDataPost(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO, Model model, @RequestParam("customJwtParameter") String customJwtParameter){

        System.out.println("customJwtParam on newform controller: "+customJwtParameter);

        List<ProcessDataDAO> results = new ArrayList<ProcessDataDAO>();
        if(processDataDAO.getFilenumber() != null){
            System.out.println("Searching data by getFilenumber");
            results = processDataRepo.findAllByFilenumber(processDataDAO.getFilenumber());
        }
        if(processDataDAO.getFname() != null && results.size() == 0){
            System.out.println("Searching data by getFname");
            results = processDataRepo.findAllByFname(processDataDAO.getFname());
        }
        if(processDataDAO.getLname() != null && results.size() == 0){
            System.out.println("Searching data by getLname");
            results = processDataRepo.findAllByLname(processDataDAO.getLname());
        }

        model.addAttribute("customJwtParameter", customJwtParameter);
        model.addAttribute("processdata", processDataDAO);
        model.addAttribute("processdatas", results);
        return "searchforms.html";
    }

    @GetMapping("/editform")
    String viewEditForm(
                    Model model,
                    @RequestParam("customJwtParameter") String customJwtParameter,
                    @RequestParam("filenumber") String filenumber){

        System.out.println("customJwtParam on newform controller: "+customJwtParameter);

        List<ProcessDataDAO> results = new ArrayList<ProcessDataDAO>();
        if(filenumber != null){
            System.out.println("Searching data by filenumber");
            results = processDataRepo.findAllByFilenumber(Integer.valueOf(filenumber));
        }

        // check to see if there are files uploaded related to this filenumber
        List<FileVO> filelist = techvvsFileHelper.getFilesByFileNumber(Integer.valueOf(filenumber), UPLOAD_DIR);

        if(filelist.size() > 0){
            model.addAttribute("filelist", filelist);
        } else {
            model.addAttribute("filelist", null);
        }


        model.addAttribute("customJwtParameter", customJwtParameter);
        model.addAttribute("processdata", results.get(0));
        return "editforms.html";
    }


    @PostMapping ("/editProcessData")
    String editProcessData(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO,
                                Model model,
                                HttpServletResponse response,
                                @RequestParam("customJwtParameter") String customJwtParameter
    ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("----------------------- START AUTH INFO ");
        System.out.println("authentication.getCredentials: "+authentication.getCredentials());
        System.out.println("authentication.getPrincipal: "+authentication.getPrincipal());
        System.out.println("authentication.getAuthorities: "+authentication.getAuthorities());
        System.out.println("----------------------- END AUTH INFO ");


        String errorResult = validateNewFormInfo(processDataDAO);

        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {

            // when creating a new processData entry, set the last attempt visit to now - this may change in future
            processDataDAO.setUpdatedtimestamp(LocalDateTime.now());

            if(processDataDAO.getActioncounter1() != null){
                processDataDAO.setActioncounter1(processDataDAO.getActioncounter1()+1);
            } else {
                processDataDAO.setActioncounter1(1);
            }

            if(processDataDAO.getActioncounter2() != null){
                processDataDAO.setActioncounter2(processDataDAO.getActioncounter2()+1);
            } else {
                processDataDAO.setActioncounter2(1);
            }


            ProcessDataDAO result = processDataRepo.save(processDataDAO);

            // check to see if there are files uploaded related to this filenumber
            List<FileVO> filelist = techvvsFileHelper.getFilesByFileNumber(processDataDAO.getFilenumber(), UPLOAD_DIR);
            if(filelist.size() > 0){
                model.addAttribute("filelist", filelist);
            } else {
                model.addAttribute("filelist", null);
            }

            model.addAttribute("successMessage","Record Successfully Saved.");
            model.addAttribute("processdata", result);
        }

        model.addAttribute("customJwtParameter", customJwtParameter);
        return "editforms.html";
    }

    @PostMapping ("/createNewProcessData")
    String createNewProcessData(@ModelAttribute( "processdata" ) ProcessDataDAO processDataDAO,
                                Model model,
                                HttpServletResponse response,
                                @RequestParam("customJwtParameter") String customJwtParameter
    ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("----------------------- START AUTH INFO ");
        System.out.println("authentication.getCredentials: "+authentication.getCredentials());
        System.out.println("authentication.getPrincipal: "+authentication.getPrincipal());
        System.out.println("authentication.getAuthorities: "+authentication.getAuthorities());
        System.out.println("----------------------- END AUTH INFO ");


        String errorResult = validateNewFormInfo(processDataDAO);

        // Validation
        if(!errorResult.equals("success")){
            model.addAttribute("errorMessage",errorResult);
        } else {

            // when creating a new processData entry, set the last attempt visit to now - this may change in future
            processDataDAO.setLastattemptvisit(LocalDateTime.now());
            processDataDAO.setCreatetimestamp(LocalDateTime.now());
            processDataDAO.setUpdatedtimestamp(LocalDateTime.now());

            if(processDataDAO.getActioncounter1() != null){
                processDataDAO.setActioncounter1(processDataDAO.getActioncounter1()+1);
            } else {
                processDataDAO.setActioncounter1(1);
            }

            if(processDataDAO.getActioncounter2() != null){
                processDataDAO.setActioncounter2(processDataDAO.getActioncounter2()+1);
            } else {
                processDataDAO.setActioncounter2(1);
            }


            ProcessDataDAO result = processDataRepo.save(processDataDAO);

            model.addAttribute("successMessage","Record Successfully Saved. ");
            model.addAttribute("processdata", result);

        }



        model.addAttribute("customJwtParameter", customJwtParameter);
        return "newform.html";
    }


    String validateNewFormInfo(ProcessDataDAO processDataDAO){


        if(processDataDAO.getFname() != null &&
                (processDataDAO.getFname().length() > 250
                || processDataDAO.getFname().length() < 1)
        ){
            return "first name must be between 1-250 characters. ";
        }

        if(processDataDAO.getLname() != null &&
                (processDataDAO.getLname().length() > 250
                || processDataDAO.getLname().length() < 1)
        ){
            return "last name must be between 1-250 characters. ";
        }

        if(
                processDataDAO.getMname() != null &&
                (processDataDAO.getMname().length() > 250
                || processDataDAO.getMname().length() < 1)
        ){
            return "middle name must be between 1-250 characters. ";
        }

        if(processDataDAO.getPhone() != null &&
                (processDataDAO.getPhone().length() > 11
                || processDataDAO.getPhone().length() < 10
                || processDataDAO.getPhone().contains("-")
                || processDataDAO.getPhone().contains("."))
        ){
            return "enter 10 or 11 digit phone number with no spaces or symbols.  ex. 18884445555";
        } else if (processDataDAO.getPhone() != null) {
            processDataDAO.setPhone(processDataDAO.getPhone().trim());
            processDataDAO.setPhone(processDataDAO.getPhone().replaceAll(" ",""));
            processDataDAO.setPhone(processDataDAO.getPhone().replaceAll("\\)",""));
        }

        if(processDataDAO.getAge() != null && (processDataDAO.getAge() > 140
                || processDataDAO.getPhone().length() < 1)
        ){
            return "age must be between 1-140. ";
        }


        if(processDataDAO.getNotes() != null && (processDataDAO.getNotes().length() > 1000)
        ){
            return "Notes must be less than 1000 characters";
        }

        if(processDataDAO.getAddress1() != null && (processDataDAO.getAddress1().length() > 200)
        ){
            return "address1 must be less than 200 characters";
        }

        if(processDataDAO.getAddress2() != null && processDataDAO.getAddress2().length() > 200
        ){
            return "address2 must be less than 200 characters";
        }

        if(processDataDAO.getRace() != null && processDataDAO.getRace().length() > 20
        ){
            return "race must be less than 20 characters";
        }

        if(processDataDAO.getCity() != null && processDataDAO.getCity().length() > 100
        ){
            return "city must be less than 100 characters";
        }

        if(processDataDAO.getState() != null && processDataDAO.getState().length() > 100
        ){
            return "state must be less than 100 characters";
        }

        if(processDataDAO.getZipcode() != null && processDataDAO.getZipcode().length() > 10
        ){
            return "zipcode must be less than 100 characters";
        }

        if(processDataDAO.getHeightfeet() != null && processDataDAO.getHeightfeet() > 10
        ){
            return "height feet must be less than 10";
        }

        if(processDataDAO.getHeightinches() != null && processDataDAO.getHeightinches() > 12
        ){
            return "height inches must be less than 12";
        }

        if(processDataDAO.getHaircolor() != null && processDataDAO.getHaircolor().length() > 20
        ){
            return "hair color must be less than 20 characters";
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
