package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.ProfessorCourseDAO;
import com.isaac.collegeapp.repo.ProfessorCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessorCourseBusinessLogicImpl implements ProfessorCourseBL{

    @Autowired
    ProfessorCourseRepository professorcourseRepository;


    @Override
    public List<ProfessorCourseDAO> filterPhdProfessorCourses(List<ProfessorCourseDAO> professorcourseDAOList) {
        return null;
    }

    @Override
    public List<ProfessorCourseDAO> getProfessorCourseDAOList() {
        System.out.println("business logic imp is executing -3");

        return professorcourseRepository.getProfessorCourseDAOList();
    }

    @Override
    public ProfessorCourseDAO getProfessorCourseById(Integer professorcourseid) {
        return null;
    }

    @Override
    public String updateProfessorCourse(ProfessorCourseDAO professorcourseDAO) {
        return professorcourseRepository.updateProfessorCourse(professorcourseDAO);
    }

    @Override
    public ProfessorCourseDAO deleteProfessorCourse(ProfessorCourseDAO professorcourseDAO) {
        return null;
    }

    @Override
    public String createProfessorCourse(ProfessorCourseDAO professorcourseDAO) {
        return professorcourseRepository.createProfessorCourse(professorcourseDAO);
    }

}
