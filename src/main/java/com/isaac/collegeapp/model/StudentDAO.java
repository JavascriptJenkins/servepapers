package com.isaac.collegeapp.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="student")
public class StudentDAO {

    // These are your data attributes for the model object
    // Step 1)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="student_id")
    Integer studentID;

    @Column(name="student_name")
    String studentName;

    Date birthday;

    @Column(name="student_id_number")
    Integer studentIDNumber;

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStudentIDNumber() {
        return studentIDNumber;
    }

    public void setStudentIDNumber(Integer studentIDNumber) {
        this.studentIDNumber = studentIDNumber;
    }





}
