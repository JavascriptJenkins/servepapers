package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllStudents() throws JsonProcessingException {

      return objectMapper.writeValueAsString(studentService.getAllStudentData()) ;

    }

    @PostMapping("/create")
    String createStudent(@RequestBody String student) throws JsonProcessingException {

        StudentDAO studentDAO=objectMapper.readValue(student,StudentDAO.class);

        String result=studentService.createStudent(studentDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateStudent(@RequestBody String student) throws JsonProcessingException {

        StudentDAO studentDAO = objectMapper.readValue(student,StudentDAO.class);

        String result =studentService.updateStudent(studentDAO);

        return result;
    }

}
