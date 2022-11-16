package com.isaac.collegeapp.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.isaac.collegeapp.security.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

// todo: add soft delete
@Entity
@Table(name="processdata")
public class ProcessDataDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    Integer id;

    @Column(name="filenumber")
    Integer filenumber;

    @Column(name="fname")
    String fname;

    @Column(name="mname")
    String mname;

    @Column(name="lname")
    String lname;

    @Column(name="age")
    Integer age;

    // used to determine how many operations have been done (edits to the data)
    @Column(name="actioncounter1")
    Integer actioncounter1;

    // used to determine how many user declared serveattempts
    @Column(name="serveattempts")
    Integer serveattempts;

    @Column(name="notes")
    String notes;

    @Column(name="address1")
    String address1;

    @Column(name="address2")
    String address2;

    @Column(name="race")
    String race;

    @Column(name="city")
    String city;

    @Column(name="state")
    String state;

    @Column(name="zipcode")
    String zipcode;

    @Column(name="heightfeet")
    Integer heightfeet;

    public Integer getHeightfeet() {
        return heightfeet;
    }

    public void setHeightfeet(Integer heightfeet) {
        this.heightfeet = heightfeet;
    }

    public Integer getHeightinches() {
        return heightinches;
    }

    public void setHeightinches(Integer heightinches) {
        this.heightinches = heightinches;
    }

    @Column(name="heightinches")
    Integer heightinches;

    @Column(name="haircolor")
    String haircolor;

    @Column(name="phone")
    String phone;

    @JsonProperty
    @Column(name="lastattemptvisit")
    LocalDateTime lastattemptvisit;



    @JsonProperty
    LocalDateTime updatedtimestamp;
    @JsonProperty
    LocalDateTime createtimestamp;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getActioncounter1() {
        return actioncounter1;
    }

    public void setActioncounter1(Integer actioncounter1) {
        this.actioncounter1 = actioncounter1;
    }

    public Integer getserveattempts() {
        return serveattempts;
    }

    public void setserveattempts(Integer serveattempts) {
        this.serveattempts = serveattempts;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getHaircolor() {
        return haircolor;
    }

    public void setHaircolor(String haircolor) {
        this.haircolor = haircolor;
    }

    public Integer getFilenumber() {
        return filenumber;
    }

    public void setFilenumber(Integer filenumber) {
        this.filenumber = filenumber;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getLastattemptvisit() {
        return lastattemptvisit;
    }

    public void setLastattemptvisit(LocalDateTime lastattemptvisit) {
        this.lastattemptvisit = lastattemptvisit;
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






}
