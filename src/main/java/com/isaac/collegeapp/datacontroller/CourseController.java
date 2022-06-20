package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.CourseDAO;
import com.isaac.collegeapp.model.CourseDAO;
import com.isaac.collegeapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/course")
@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllCourses() throws JsonProcessingException {

      return objectMapper.writeValueAsString(courseService.getAllCourseData()) ;

    }



    @PostMapping("/create")
    String createCourse(@RequestBody String course) throws JsonProcessingException {

        CourseDAO courseDAO =objectMapper.readValue(course, CourseDAO.class);

        String result=courseService.createCourse(courseDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateCourse(@RequestBody String course) throws JsonProcessingException {

        CourseDAO courseDAO = objectMapper.readValue(course, CourseDAO.class);

        String result = courseService.updateCourse(courseDAO);

        return result;
    }

    @DeleteMapping ("/delete")
    String deleteCourse(@RequestBody String course) throws JsonProcessingException {

        CourseDAO courseDAO = objectMapper.readValue(course,CourseDAO.class);

        String result = courseService.deleteCourse(courseDAO);

        return result;
    }

}
