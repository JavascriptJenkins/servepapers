package com.isaac.collegeapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.isaac.collegeapp.security.Role;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="systemuser")
public class SystemUserDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    Integer id;

    @Column(name="password")
    String password;

    @Nullable
    @Column(name="name")
    String name;

    @Nullable
    @Column(name="project")
    String project;

    @Transient
    String password2;

    @Column(name="email")
    String email;

    @Column(name="roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn
    Role[] roles;

    @Column(name="phone")
    String phone;

    @Column(name="isuseractive")
    Integer isuseractive;

    @JsonProperty
    java.time.LocalDateTime updatedtimestamp;
    @JsonProperty
    java.time.LocalDateTime createtimestamp;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsuseractive() {
        return isuseractive;
    }

    public void setIsuseractive(Integer isuseractive) {
        this.isuseractive = isuseractive;
    }

    public LocalDateTime getUpdatedtimestamp() {
        return updatedtimestamp;
    }

    public void setUpdatedtimestamp(LocalDateTime updatedtimestamp) {
        this.updatedtimestamp = updatedtimestamp;
    }

    public LocalDateTime getCreatetimestamp() {
        return createtimestamp;
    }

    public void setCreatetimestamp(LocalDateTime createtimestamp) {
        this.createtimestamp = createtimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(@Nullable String project) {
        this.project = project;
    }






}
