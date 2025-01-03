package com.example.firstapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//Handles database creation and versioning.
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 2; // Increment the version

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TASKS = "tasks";

    // Columns for the users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    //Columns for tasks tables
    public static final String TASK_ID = "task_id";
    public static final String COLUMN_TASK = "task";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(CREATE_TABLE_USERS);
            db.execSQL(CREATE_TABLE_TASKS);
            onCreate(db);
        }
    }

    // SQL to create the users table table of database.
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_USER_PASSWORD + " TEXT NOT NULL);";


    // Existing tasks table creation
    private static final String CREATE_TABLE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    TASK_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK + " TEXT NOT NULL, " +
                    COLUMN_USER_ID + " INTEGER NOT NULL, " + // Foreign key column
                    "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "));";  // Each task belongs to a valid user in the users table.


}

