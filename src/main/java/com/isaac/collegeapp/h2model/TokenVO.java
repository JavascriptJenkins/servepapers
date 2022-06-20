package com.isaac.collegeapp.h2model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="token")
@JsonIgnoreProperties
public class TokenVO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    Long id;

    @JsonProperty
    String token;
    @JsonProperty
    String usermetadata; // for storing browser info etc (and hashed version of email)
    @JsonProperty
    int tokenused;
    @JsonProperty
    java.time.LocalDateTime updatedtimestamp;
    @JsonProperty
    java.time.LocalDateTime createtimestamp;
    @JsonProperty
    @Transient
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsermetadata() {
        return usermetadata;
    }

    public void setUsermetadata(String usermetadata) {
        this.usermetadata = usermetadata;
    }

    public int getTokenused() {
        return tokenused;
    }

    public void setTokenused(int tokenused) {
        this.tokenused = tokenused;
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



}