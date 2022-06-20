package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/studentView")
@Controller
public class StudentViewController {

    @Autowired
    StudentService studentService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    HttpServletRequest httpServletRequest;



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


}
