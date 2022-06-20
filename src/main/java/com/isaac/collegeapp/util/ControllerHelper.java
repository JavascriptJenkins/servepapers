package com.isaac.collegeapp.util;

import com.isaac.collegeapp.jparepo.StudentJpaRepo;
import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class ControllerHelper {

    @Autowired
    StudentJpaRepo studentJpaRepo;


    public void checkForLoggedInStudent(Model model, HttpServletRequest httpServletRequest){

        Cookie[] cookelist = httpServletRequest.getCookies();

        if(cookelist != null){
            Cookie cookie = cookelist[0];

            String studentIDNumber = cookie.getValue(); // this value is a studentIDNumber

            StudentDAO studentDAO = studentJpaRepo.findByStudentIDNumber(Integer.valueOf(studentIDNumber));

            if(studentDAO != null){
                model.addAttribute("loggedInUserName", studentDAO.getStudentName());
            }

        }

    }

}
