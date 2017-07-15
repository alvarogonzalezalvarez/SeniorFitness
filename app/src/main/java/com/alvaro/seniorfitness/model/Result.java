package com.alvaro.seniorfitness.model;

public class Result {

    private String userID;
    private String testID;
    private String sessionID;
    private String result;
    private String date;

    public Result(String userID, String testID, String sessionID, String result, String date) {
        this.userID = userID;
        this.testID = testID;
        this.sessionID = sessionID;
        this.result = result;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
