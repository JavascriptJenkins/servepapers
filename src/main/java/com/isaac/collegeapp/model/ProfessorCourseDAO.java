package com.isaac.collegeapp.model;

import java.sql.Date;

public class ProfessorCourseDAO {

    // These are your data attributes for the model object
    // Step 1)
    Integer professor_course_id;
    Integer professor_id;
    Integer course_id;

    // below here are the getters and setters

    public Integer getProfessor_course_id() {
        return professor_course_id;
    }

    public void setProfessor_course_id(Integer professor_course_id) {
        this.professor_course_id = professor_course_id;
    }

    public Integer getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(Integer professor_id) {
        this.professor_id = professor_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }








}
