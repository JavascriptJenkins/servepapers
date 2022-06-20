package com.isaac.collegeapp.h2model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="cancel_train")
@JsonIgnoreProperties
public class CancelTrainVO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    Long id;
    @JsonProperty
    String fname;
    @JsonProperty
    String lname;
    @JsonProperty
    boolean iscanceled;
    @JsonProperty
    int upvotes;
    @JsonProperty
    int downvotes;
    @JsonProperty
    int cancelstatus;
    @JsonProperty
    String why;
    @JsonProperty
    String imageurl;



    @JsonProperty
    java.time.LocalDateTime updatedtimestamp;
    @JsonProperty
    java.time.LocalDateTime createtimestamp;
    @JsonProperty
    @Transient
    int incomingVote;
    @JsonProperty
    @Transient
    int progressbar;
    @JsonProperty
    @Transient
    String email;

    @JsonProperty
    @Transient
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIncomingVote() {
        return incomingVote;
    }

    public void setIncomingVote(int incomingVote) {
        this.incomingVote = incomingVote;
    }

    public int getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(int progressbar) {
        this.progressbar = progressbar;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public boolean isIscanceled() {
        return iscanceled;
    }

    public void setIscanceled(boolean iscanceled) {
        this.iscanceled = iscanceled;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public int getCancelstatus() {
        return cancelstatus;
    }

    public void setCancelstatus(int cancelstatus) {
        this.cancelstatus = cancelstatus;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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