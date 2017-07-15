package com.alvaro.seniorfitness.model;

public class Session {

    private String sessionID;
    private String userID;
    private String active;
    private String date;

    public Session(String sessionID, String userID, String active, String date) {
        this.sessionID = sessionID;
        this.userID = userID;
        this.active = active;
        this.date = date;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
