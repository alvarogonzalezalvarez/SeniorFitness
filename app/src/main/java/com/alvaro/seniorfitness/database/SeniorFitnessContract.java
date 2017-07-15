package com.alvaro.seniorfitness.database;


import android.provider.BaseColumns;

public class SeniorFitnessContract {

    private SeniorFitnessContract() {}

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERID = "userid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_BIRTHDATE = "birthdate";
    }

    public static class Result implements BaseColumns {
        public static final String TABLE_NAME = "result";
        public static final String COLUMN_NAME_USERID = "userid";
        public static final String COLUMN_NAME_SESSIONID = "sessionid";
        public static final String COLUMN_NAME_TESTID = "testid";
        public static final String COLUMN_NAME_RESULT = "result";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static class Test implements BaseColumns {
        public static final String TABLE_NAME = "test";
        public static final String COLUMN_NAME_TESTID = "testid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    public static class Session implements BaseColumns {
        public static final String TABLE_NAME = "session";
        public static final String COLUMN_NAME_SESSIONID = "sessionid";
        public static final String COLUMN_NAME_USERID = "userid";
        public static final String COLUMN_NAME_ACTIVE = "active";
        public static final String COLUMN_NAME_DATE = "date";
    }

}
