package com.alvaro.seniorfitness.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeniorFitnessDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "SeniorFitness.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE IF NOT EXISTS " + SeniorFitnessContract.User.TABLE_NAME + " (" +
                    SeniorFitnessContract.User.COLUMN_NAME_USERID + " TEXT PRIMARY KEY," +
                    SeniorFitnessContract.User.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.User.COLUMN_NAME_LASTNAME + TEXT_TYPE  + COMMA_SEP +
                    SeniorFitnessContract.User.COLUMN_NAME_GENDER + TEXT_TYPE  + COMMA_SEP +
                    SeniorFitnessContract.User.COLUMN_NAME_BIRTHDATE + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.User.COLUMN_NAME_PHOTO + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + SeniorFitnessContract.User.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_RESULT =
            "CREATE TABLE IF NOT EXISTS " + SeniorFitnessContract.Result.TABLE_NAME + " (" +
                    SeniorFitnessContract.Result.COLUMN_NAME_USERID + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_TESTID + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_RESULT + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_DATE + " TEXT, PRIMARY KEY(" +
                    SeniorFitnessContract.Result.COLUMN_NAME_USERID + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID + COMMA_SEP +
                    SeniorFitnessContract.Result.COLUMN_NAME_TESTID +
                    " ))";

    private static final String SQL_DELETE_ENTRIES_RESULT =
            "DROP TABLE IF EXISTS " + SeniorFitnessContract.Result.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_TEST =
            "CREATE TABLE IF NOT EXISTS " + SeniorFitnessContract.Test.TABLE_NAME + " (" +
                    SeniorFitnessContract.Test.COLUMN_NAME_TESTID + " TEXT PRIMARY KEY," +
                    SeniorFitnessContract.Test.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Test.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES_TEST =
            "DROP TABLE IF EXISTS " + SeniorFitnessContract.Test.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_SESSION =
            "CREATE TABLE IF NOT EXISTS " + SeniorFitnessContract.Session.TABLE_NAME + " (" +
                    SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Session.COLUMN_NAME_USERID + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE + TEXT_TYPE + COMMA_SEP +
                    SeniorFitnessContract.Session.COLUMN_NAME_DATE + " TEXT, PRIMARY KEY(" +
                    SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID + COMMA_SEP +
                    SeniorFitnessContract.Session.COLUMN_NAME_USERID +
                    " ))";

    private static final String SQL_DELETE_ENTRIES_SESSION =
            "DROP TABLE IF EXISTS " + SeniorFitnessContract.Session.TABLE_NAME;

    public SeniorFitnessDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_TEST);
        db.execSQL(SQL_CREATE_ENTRIES_SESSION);
        db.execSQL(SQL_CREATE_ENTRIES_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_TEST);
        db.execSQL(SQL_DELETE_ENTRIES_SESSION);
        db.execSQL(SQL_DELETE_ENTRIES_RESULT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}