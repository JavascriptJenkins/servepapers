package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentBL {

   public List<StudentDAO> filterPhdStudents (List<StudentDAO> studentDAOList);
   public List<StudentDAO> getStudentDAOList ();
   public StudentDAO getStudentById(Integer studentid);
   public String updateStudent(StudentDAO studentDAO);
   public  StudentDAO deleteStudent(StudentDAO studentDAO);
   public String createStudent(StudentDAO studentDAO);

}
