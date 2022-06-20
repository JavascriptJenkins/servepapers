package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.StudentCourseDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentCourseBL {

   public List<StudentCourseDAO> filterPhdStudentCourses (List<StudentCourseDAO> studentcourseDAOList);
   public List<StudentCourseDAO> getStudentCourseDAOList ();
   public StudentCourseDAO getStudentCourseById(Integer studentcourseid);
   public  String updateStudentCourse(StudentCourseDAO studentcourseDAO);
   public  StudentCourseDAO deleteStudentCourse(StudentCourseDAO studentcourseDAO);
   public String createStudentCourse(StudentCourseDAO studentcourseDAO);

}
