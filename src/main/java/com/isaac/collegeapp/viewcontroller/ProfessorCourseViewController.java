package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.ProfessorCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/professorcourseView")
@Controller
public class ProfessorCourseViewController {

    @Autowired
    ProfessorCourseService professorcourseService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewProfessorCourses")
    String viewProfessorCourses(Model model){

        model.addAttribute("professorcourses", professorcourseService.getAllProfessorCourseData());
        return "viewProfessorCourses.html";
    }





}
