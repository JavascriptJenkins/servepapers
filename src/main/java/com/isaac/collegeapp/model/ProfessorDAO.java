package com.isaac.collegeapp.model;

import java.sql.Date;

public class ProfessorDAO {

    // These are your data attributes for the model object
    // Step 1)
    Integer professor_id;
    String professor_name;
    Date hire_date;
    Integer has_phd;

    // below here are the getters and setters
    public Integer getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(Integer professor_id) {
        this.professor_id = professor_id;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }

    public Integer getHas_phd() {
        return has_phd;
    }

    public void setHas_phd(Integer has_phd) {
        this.has_phd = has_phd;
    }



}
