package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.StudentCourseDAO;
import com.isaac.collegeapp.repo.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentCourseBusinessLogicImpl implements StudentCourseBL{

    @Autowired
    StudentCourseRepository studentcourseRepository;


    @Override
    public List<StudentCourseDAO> filterPhdStudentCourses(List<StudentCourseDAO> studentcourseDAOList) {
        return null;
    }

    @Override
    public List<StudentCourseDAO> getStudentCourseDAOList() {
        System.out.println("business logic imp is executing -3");

        return studentcourseRepository.getStudentCourseDAOList();
    }

    @Override
    public StudentCourseDAO getStudentCourseById(Integer studentcourseid) {
        return null;
    }

    @Override
    public String updateStudentCourse(StudentCourseDAO studentcourseDAO) {
        return studentcourseRepository.updateStudentCourse(studentcourseDAO);
    }

    @Override
    public StudentCourseDAO deleteStudentCourse(StudentCourseDAO studentcourseDAO) {
        return null;
    }

    @Override
    public String createStudentCourse(StudentCourseDAO studentcourseDAO) {
        return studentcourseRepository.createStudentCourse(studentcourseDAO);
    }
}
