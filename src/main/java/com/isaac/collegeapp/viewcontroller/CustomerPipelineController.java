package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import com.isaac.collegeapp.email.EmailManager;
import com.isaac.collegeapp.jparepo.ProcessDataRepo;
import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.jparepo.TokenRepo;
import com.isaac.collegeapp.model.ProcessDataDAO;
import com.isaac.collegeapp.modelnonpersist.FileVO;
import com.isaac.collegeapp.modelnonpersist.CustPipelineVO;

import com.isaac.collegeapp.security.UserService;
import com.isaac.collegeapp.service.NewFormService;
import com.isaac.collegeapp.service.StudentService;
import com.isaac.collegeapp.util.TechvvsFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationFeature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RequestMapping("/customer")
@RestController // returns json data, not views
public class CustomerPipelineController {

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
    NewFormService newFormService;

    @Autowired
    TokenRepo tokenRepo;

    SecureRandom secureRandom = new SecureRandom();

    @Autowired
    EmailManager emailManager;


    // no authentication needed for this method because it's running on same local server
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/pipeline")
    String searchProcessDataPost(@RequestBody String data){

        System.out.println("coming from react /pipeline: "+data);


        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try{

        // This is parsing correctly from react data now
       JsonNode node = mapper.readTree(data);
       System.out.println("noe: " + node.get("name"));
       System.out.println("value: " + node.get("name").get("value"));



       // parse out the 3 values from the form input json

        // make a new systemuserobject and insert into database(take code from other controller for this)

       // make sure validation is done on this object and also that user active is set to 0


        } catch(Exception exception){
                         System.out.println("exe with json: " + exception);

        }
    

        return data;
    }







}
