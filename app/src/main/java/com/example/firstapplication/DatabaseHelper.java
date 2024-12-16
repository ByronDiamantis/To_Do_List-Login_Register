package com.example.firstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

//Handles database creation and versioning.
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 2; // Increment the version

   // public SQLiteDatabase db;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TASKS = "tasks";

    // Columns for the users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    private static DatabaseHelper instance;


    //Columns for tasks tables
    public static final String COLUMN_TASK = "task";
    private Context context;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(CREATE_TABLE_USERS);
        }

    }

    // Static method to get the single instance of the helper
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Example of getting a writable database
    public static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "task TEXT NOT NULL);";



//    // Register a new user
//    public long registerUser(String email, String password) {
//        //SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_EMAIL, email);
//        values.put(COLUMN_USER_PASSWORD, password);
//
//        return db.insert(TABLE_USERS, null, values);
//    }
//
//    // Check if login is valid
//    public boolean validateUser(String email, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(
//                TABLE_USERS,
//                new String[]{COLUMN_USER_ID},
//                COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
//                new String[]{email, password},
//                null, null, null
//        );
//        boolean isValid = cursor.moveToFirst();
//        cursor.close();
//        return isValid;
//    }
//
//    // Insert a new task
//    public boolean insertTask(String task) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TASK, task);
//        long result = db.insert(TABLE_TASKS, null, values);
//        return result != -1; // Return true if insert is successful
//    }
//
//    // Retrieve all tasks
//    public ArrayList<String> getAllTasks() {
//        ArrayList<String> tasks = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_TASKS, new String[]{COLUMN_TASK}, null, null, null, null, null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                tasks.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK)));
//            }
//            cursor.close();
//        }
//        return tasks;
//    }
//
//    // Delete a task
//    public void deleteTask(String task) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TASKS, COLUMN_TASK + " = ?", new String[]{task});
//    }
}

