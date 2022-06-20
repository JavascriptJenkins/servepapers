package com.isaac.collegeapp.businesslogic;


import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentBusinessLogicImpl implements StudentBL{

    @Autowired
    StudentRepository studentRepository;


    @Override
    public List<StudentDAO> filterPhdStudents(List<StudentDAO> studentDAOList) {
        return null;
    }

    @Override
    public List<StudentDAO> getStudentDAOList() {
        System.out.println("business logic imp is executing -3");

        return studentRepository.getStudentDAOList();
    }

    @Override
    public StudentDAO getStudentById(Integer studentid) {
        return null;
    }

    @Override
    public String updateStudent(StudentDAO studentDAO) {
        return studentRepository.createStudent(studentDAO);
    }

    @Override
    public StudentDAO deleteStudent(StudentDAO studentDAO) {
        return null;
    }

    @Override
    public String createStudent(StudentDAO studentDAO) {

        // students that are being created on the HTML user interface will not have a student ID by default
        if(studentDAO.getStudentIDNumber() == null){
            int min = 10000;
            int max = 100000;

            //Generate random int value from 50 to 100
            System.out.println("Random value in int from "+min+" to "+max+ ":");
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            System.out.println(random_int);

            studentDAO.setStudentIDNumber(random_int);
        }


        return studentRepository.createStudent(studentDAO);
    }


}
