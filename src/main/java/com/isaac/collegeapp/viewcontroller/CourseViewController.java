package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/courseView")
@Controller
public class CourseViewController {

    @Autowired
    CourseService courseService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewCourses")
    String viewCourses(Model model){

        model.addAttribute("courses", courseService.getAllCourseData());
        return "viewCourses.html";
    }

    @GetMapping("/viewRealEstate")
    String viewRealEstate(){

        return "viewRealEstate.html";
    }




}
