package com.alvaro.seniorfitness.model;

public class Test {

    private String testID;
    private String name;
    private String description;
    private String result;

    public Test(String testID, String name, String description, String result) {
        this.testID = testID;
        this.name = name;
        this.description = description;
        this.result = result;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
