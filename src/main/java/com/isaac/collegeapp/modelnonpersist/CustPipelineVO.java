package com.isaac.collegeapp.modelnonpersist;

import com.fasterxml.jackson.annotation.JsonProperty;



public class CustPipelineVO {

    @JsonProperty
    public String name;

    @JsonProperty
    public String email;
    @JsonProperty
    public String project;

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

        public String getproject() {
        return project;
    }

    public void setproject(String project) {
        this.project = project;
    }
}
