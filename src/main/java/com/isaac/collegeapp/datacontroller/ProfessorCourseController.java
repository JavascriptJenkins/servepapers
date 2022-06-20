package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.ProfessorCourseDAO;
import com.isaac.collegeapp.model.ProfessorCourseDAO;
import com.isaac.collegeapp.service.ProfessorCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/professorcourse")
@RestController
public class ProfessorCourseController {

    @Autowired
    ProfessorCourseService professorcourseService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllProfessorCourses() throws JsonProcessingException {

      return objectMapper.writeValueAsString(professorcourseService.getAllProfessorCourseData()) ;

    }

    @PostMapping("/create")
    String createProfessorCourse(@RequestBody String professorcourse) throws JsonProcessingException {

        ProfessorCourseDAO professorcourseDAO=objectMapper.readValue(professorcourse, ProfessorCourseDAO.class);

        String result=professorcourseService.createProfessorCourse(professorcourseDAO);

        return result;

    }
    @PostMapping ("/update")
    String updateProfessorCourse(@RequestBody String professorcourse) throws JsonProcessingException {

        ProfessorCourseDAO professorcourseDAO = objectMapper.readValue(professorcourse, ProfessorCourseDAO.class);

        String result = professorcourseService.updateProfessorCourse(professorcourseDAO);

        return result;
    }

}
