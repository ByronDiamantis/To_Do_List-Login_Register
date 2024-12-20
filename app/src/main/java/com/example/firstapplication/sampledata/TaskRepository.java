package com.example.firstapplication.sampledata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.firstapplication.DatabaseHelper;

import java.util.ArrayList;

//Handle Task-Related Queries
public class TaskRepository {

    private final SQLiteDatabase db;

    public TaskRepository(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean insertTask(String task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TASK, task);
        long result = db.insert(DatabaseHelper.TABLE_TASKS, null, values);
        return result != -1;
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

