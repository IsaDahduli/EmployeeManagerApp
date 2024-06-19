package com.isa.employeemanagerapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmployeeDatabaseHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "employees.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EID = "employeeID";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_HIRE_DATE = "hiredate";
    public static final String COLUMN_ISWORKING = "isworking";
    public static final String COLUMN_JOB = "job";
    public static final String COLUMN_SALARY = "salary";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMPLOYEES + " (" +
                    COLUMN_EID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_HIRE_DATE + " TEXT, " +
                    COLUMN_ISWORKING + " TEXT, " +
                    COLUMN_JOB + " TEXT, " +
                    COLUMN_SALARY + " TEXT " + ") ";

    public EmployeeDatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL(TABLE_CREATE);
    }
}
