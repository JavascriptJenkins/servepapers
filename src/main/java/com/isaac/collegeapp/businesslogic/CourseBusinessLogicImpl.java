package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.CourseDAO;
import com.isaac.collegeapp.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseBusinessLogicImpl implements CourseBL{

    @Autowired
    CourseRepository courseRepository;


    @Override
    public List<CourseDAO> filterPhdCourses(List<CourseDAO> courseDAOList) {
        return null;
    }

    @Override
    public List<CourseDAO> getCourseDAOList() {
        System.out.println("business logic imp is executing -3");

        return courseRepository.getCourseDAOList();
    }

    @Override
    public CourseDAO getCourseById(Integer courseid) {
        return null;
    }

    @Override
    public String updateCourse(CourseDAO courseDAO) {
        return courseRepository.updateCourse(courseDAO);
    }

    @Override
    public String deleteCourse(CourseDAO courseDAO) {
        return  courseRepository.deleteCourse(courseDAO);
    }

    @Override
    public String createCourse(CourseDAO courseDAO) {
        return null;
    }
}
