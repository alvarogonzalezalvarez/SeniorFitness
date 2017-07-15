package com.alvaro.seniorfitness.model;

public class User {

    private String userID;
    private String name;
    private String lastname;
    private String gender;
    private String birthdate;

    public User(String userID, String name, String lastname, String gender, String birthdate) {
        this.userID = userID;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
