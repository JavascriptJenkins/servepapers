package com.isaac.collegeapp.model;

import java.sql.Date;

public class CourseDAO {




    // These are your data attributes for the model object
    // Step 1)
    Integer course_id;
    Integer room_id;
    String course_name;
    Integer creds;
    String course_id_desc;


    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Integer getCreds() {
        return creds;
    }

    public void setCreds(Integer creds) {
        this.creds = creds;
    }

    public String getCourse_id_desc() {
        return course_id_desc;
    }

    public void setCourse_id_desc(String course_id_desc) {
        this.course_id_desc = course_id_desc;
    }



}
