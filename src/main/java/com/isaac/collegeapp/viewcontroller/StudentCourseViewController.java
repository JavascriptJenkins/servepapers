package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.ProfessorService;
import com.isaac.collegeapp.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/studentCourseView")
@Controller
public class StudentCourseViewController {

    @Autowired
    StudentCourseService studentCourseService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewStudentCourse")
    String viewProfessors(Model model){

        model.addAttribute("studentCourse", studentCourseService.getAllStudentCourseData());
        return "viewStudentCourse.html";
    }





}
