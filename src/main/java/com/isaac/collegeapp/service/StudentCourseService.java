package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.StudentCourseBL;
import com.isaac.collegeapp.model.StudentCourseDAO;
import com.isaac.collegeapp.model.StudentCourseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentCourseService {

    @Autowired
    StudentCourseBL studentcourseBL;

   public List<StudentCourseDAO> getAllStudentCourseData() {

       System.out.println("service layer is now executing-2");


        return studentcourseBL.getStudentCourseDAOList();
    }

    public String createStudentCourse(StudentCourseDAO studentcourseDAO) {

        System.out.println("service layer is now executing-2");


        return studentcourseBL.createStudentCourse(studentcourseDAO);
    }

    public String updateStudentCourse(StudentCourseDAO studentcourseDAO) {

        System.out.println("service layer is now executing-2");


        return studentcourseBL.updateStudentCourse(studentcourseDAO);
    }
}
