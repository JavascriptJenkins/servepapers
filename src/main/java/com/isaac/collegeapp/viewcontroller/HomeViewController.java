package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequestMapping("/dashboard")
@Controller
public class HomeViewController {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;



    @GetMapping("/index")
    String index(Model model, @RequestParam("customJwtParameter") String token){

        System.out.println("hit the dashboard index page");
        model.addAttribute("customJwtParameter",token);
//        model.addAttribute(Objects.requireNonNull(model.getAttribute("customJwtParameter")));
//
//        System.out.println("PARAM ON CONTROLLER: " + model.getAttribute("customJwtParameter"));
//        System.out.println( httpServletRequest);

       // model.addAttribute("student", new StudentDAO());
        return "index.html";
    }


   // @PostMapping("/login")

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
