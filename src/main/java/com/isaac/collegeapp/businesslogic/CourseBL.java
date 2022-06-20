package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.CourseDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseBL {

   public List<CourseDAO> filterPhdCourses (List<CourseDAO> courseDAOList);
   public List<CourseDAO> getCourseDAOList ();
   public CourseDAO getCourseById(Integer courseid);
   public  String updateCourse(CourseDAO courseDAO);
   public  String deleteCourse(CourseDAO courseDAO);
   public String createCourse(CourseDAO courseDAO);

}
