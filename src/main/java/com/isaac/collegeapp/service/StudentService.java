package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.StudentBL;
import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService {

    @Autowired
    StudentBL studentBL;

   public List<StudentDAO> getAllStudentData() {

       System.out.println("service layer is now executing-2");


        return studentBL.getStudentDAOList();
    }



    public String createStudent(StudentDAO studentDAO) {

        System.out.println("service layer is now executing-2");


        return studentBL.createStudent(studentDAO);
    }

    public String updateStudent(StudentDAO studentDAO) {

        System.out.println("service layer is now executing-2");


        return studentBL.updateStudent(studentDAO);
    }


}
