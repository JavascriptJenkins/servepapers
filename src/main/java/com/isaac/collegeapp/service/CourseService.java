package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.CourseBL;
import com.isaac.collegeapp.model.CourseDAO;
import com.isaac.collegeapp.model.CourseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseService {

    @Autowired
    CourseBL courseBL;

   public List<CourseDAO> getAllCourseData() {

       System.out.println("service layer is now executing-2");


        return courseBL.getCourseDAOList();
    }

    public String createCourse(CourseDAO courseDAO) {

        System.out.println("service layer is now executing-2");


        return courseBL.createCourse(courseDAO);
    }

    public String updateCourse(CourseDAO courseDAO) {

        System.out.println("service layer is now executing-2");


        return courseBL.updateCourse(courseDAO);
    }

    public String deleteCourse(CourseDAO courseDAO) {

        System.out.println("service layer is now executing-2");


        return courseBL.deleteCourse(courseDAO);
    }
    
}
