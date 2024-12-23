package com.example.firstapplication.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.firstapplication.Database.DatabaseHelper;

import java.util.ArrayList;

//Handle Task-Related Queries
public class TaskRepository extends DatabaseHelper{

    private final SQLiteDatabase db;

    public TaskRepository(SQLiteDatabase db, Context context) {
        super(context);
        this.db = db;
    }

    public ArrayList<String> getAllTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TASKS, new String[]{DatabaseHelper.COLUMN_TASK}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                tasks.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK)));
            }
            cursor.close();
        }
        return tasks;
    }

    public void deleteTask(String task) {
        db.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.COLUMN_TASK + " = ?", new String[]{task});
    }
}

