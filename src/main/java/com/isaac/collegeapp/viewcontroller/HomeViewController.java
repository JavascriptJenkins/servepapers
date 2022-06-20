package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.jparepo.StudentJpaRepo;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.service.BookService;
import com.isaac.collegeapp.util.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/home")
@Controller
public class HomeViewController {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired StudentJpaRepo studentJpaRepo;

    @Autowired
    ControllerHelper controllerHelper;

    @GetMapping
    String index(Model model){

        controllerHelper.checkForLoggedInStudent(model, httpServletRequest); // this will check to see if a student has already loggede in

        System.out.println( httpServletRequest);

        model.addAttribute("student", new StudentDAO());
        return "index.html";
    }


    @PostMapping("/login")
    String login(@ModelAttribute( "student" ) StudentDAO studentDAO, Model model){

        System.out.println("someone is logging in");

        StudentDAO student = studentJpaRepo.findByStudentNameAndStudentIDNumber(studentDAO.getStudentName(), studentDAO.getStudentIDNumber());

        if(student == null){
            // display error message
            model.addAttribute("errorMessage", "Student Not Found");

        } else {
            // display success message
            model.addAttribute("successMessage", "Student Found, logged in Successfully as Student: "+student.getStudentName());


            // put cookie in browser
            Cookie cookie = new Cookie("Authorization", student.getStudentIDNumber().toString());
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            // add the student name to the model so we can see it in Navbar
            model.addAttribute("loggedInUserName", student.getStudentName());

        }



        return "index.html";
    }

    @PostMapping("/logout")
    String logout(Model model){

        model.addAttribute("student", new StudentDAO());

        System.out.println("someone is logging out");

        Cookie cookie = new Cookie("Authorization", null); // Not necessary, but saves bandwidth.
        cookie.setPath("/");
        cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        httpServletResponse.addCookie(cookie);

        return "index.html";
    }



    //  This will add an outgoing repsonse header to everything that hits this controller




}
