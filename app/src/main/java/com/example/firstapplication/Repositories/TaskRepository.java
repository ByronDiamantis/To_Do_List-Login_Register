package com.example.firstapplication.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.firstapplication.Database.DatabaseHelper;
import java.util.ArrayList;
import com.example.firstapplication.Models.Task;


//Handle Task-Related Queries
public class TaskRepository extends DatabaseHelper{

    private final SQLiteDatabase db;

    public TaskRepository(SQLiteDatabase db, Context context) {
        super(context);
        this.db = db;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TASKS, new String[]{DatabaseHelper.COLUMN_TASK}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Create a Task object for each row
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK));
                Task task = new Task(taskName);
                tasks.add(task);
            }
            cursor.close();
        }
        return tasks;
    }

    public Task deleteTask(String deleteTask) {
        // Retrieve the task before deleting it
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.COLUMN_TASK},
                DatabaseHelper.COLUMN_TASK + " = ?",
                new String[]{deleteTask},
                null, null, null
        );

        Task task = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Create the Task object
            task = new Task(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK)));
            cursor.close();
        }

        // Delete the task from the database
        db.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.COLUMN_TASK + " = ?", new String[]{deleteTask});

        // Return the deleted Task object (or null if the task was not found)
        return task;
    }
}

