package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface StudentJpaRepo extends JpaRepository<StudentDAO, Integer> {


    // this will generate a sql statement based on what you name this method
    // example:  select * where student_name = 1 s
    StudentDAO findByStudentNameAndStudentIDNumber(String studentName, Integer studentIDNumber);
    StudentDAO findByStudentIDNumber(Integer studentIDNumber);


}
