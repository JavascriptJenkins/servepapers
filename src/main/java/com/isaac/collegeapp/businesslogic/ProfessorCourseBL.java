package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.ProfessorCourseDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProfessorCourseBL {

   public List<ProfessorCourseDAO> filterPhdProfessorCourses (List<ProfessorCourseDAO> professorcourseDAOList);
   public List<ProfessorCourseDAO> getProfessorCourseDAOList ();
   public ProfessorCourseDAO getProfessorCourseById(Integer professorcourseid);
   public  String updateProfessorCourse(ProfessorCourseDAO professorcourseDAO);
   public  ProfessorCourseDAO deleteProfessorCourse(ProfessorCourseDAO professorcourseDAO);
   public String createProfessorCourse(ProfessorCourseDAO professorcourseDAO);

}
