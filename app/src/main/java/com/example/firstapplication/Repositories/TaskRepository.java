package com.example.firstapplication.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.firstapplication.Database.DatabaseHelper;
import java.util.ArrayList;
import com.example.firstapplication.Models.Task;


//Handle Task-Related Queries
public class TaskRepository {
    private final SQLiteDatabase db;

    public TaskRepository(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context); // Create a DatabaseHelper instance
        this.db = dbHelper.getWritableDatabase(); // Get the writable database
    }

    // Retrieve tasks for a specific user
    public ArrayList<Task> getTasksOfCurrentUser(int userId) {
        ArrayList<Task> tasks = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.TASK_ID, DatabaseHelper.COLUMN_TASK}, // Include TASK_ID here
                DatabaseHelper.COLUMN_USER_ID + " = ?", // Filter by user_id
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Retrieve task data from the cursor
                int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TASK_ID));
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK));

                // Create a Task object
                Task task = new Task(taskId, userId, taskName);

                tasks.add(task);
            }
            cursor.close();
        }
        return tasks;
    }

    // Add a task for a specific user
    public void addTask(String taskName, int userId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TASK, taskName);
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        db.insert(DatabaseHelper.TABLE_TASKS, null, values);
    }

    // Delete a specific task for a specific user
    public void deleteTask(String taskName, int userId) {
        // Retrieve the task before deleting it
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.TASK_ID, DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_TASK}, // Fetch all required columns
                DatabaseHelper.COLUMN_TASK + " = ? AND " + DatabaseHelper.COLUMN_USER_ID + " = ?", // Filter by task name and userId
                new String[]{taskName, String.valueOf(userId)},
                null, null, null
        );

        Task task = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve all columns and create a Task object
            int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TASK_ID));
            int taskUserId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID));
            String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK));

            task = new Task(taskId, taskUserId, taskTitle);
            cursor.close();
        }

        // Delete the task
        db.delete(
                DatabaseHelper.TABLE_TASKS,
                DatabaseHelper.COLUMN_TASK + " = ? AND " + DatabaseHelper.COLUMN_USER_ID + " = ?",
                new String[]{taskName, String.valueOf(userId)}
        );

    }
}

