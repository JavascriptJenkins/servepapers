package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.StudentCourseDAO;
import com.isaac.collegeapp.model.StudentCourseDAO;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/studentcourse")
@RestController
public class StudentCourseController {

    @Autowired
    StudentCourseService studentcourseService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllStudentCourses() throws JsonProcessingException {

      return objectMapper.writeValueAsString(studentcourseService.getAllStudentCourseData()) ;

    }

    @PostMapping("/create")
    String createStudentCourse(@RequestBody String studentcourse) throws JsonProcessingException {

        StudentCourseDAO studentcourseDAO=objectMapper.readValue(studentcourse,StudentCourseDAO.class);

        String result=studentcourseService.createStudentCourse(studentcourseDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateStudentCourse(@RequestBody String studentcourse) throws JsonProcessingException {

        StudentCourseDAO studentcourseDAO = objectMapper.readValue(studentcourse, StudentCourseDAO.class);

        String result = studentcourseService.updateStudentCourse(studentcourseDAO);

        return result;
    }

}
