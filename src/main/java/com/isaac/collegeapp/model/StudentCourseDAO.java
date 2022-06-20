package com.isaac.collegeapp.model;

public class StudentCourseDAO {


    // These are your data attributes for the model object
    // Step 1)
    Integer student_course_id;
    Integer student_id;
    Integer course_id;

    // below here are the getters and setters



    public Integer getStudent_course_id() {
        return student_course_id;
    }

    public void setStudent_course_id(Integer student_course_id) {
        this.student_course_id = student_course_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }


}
