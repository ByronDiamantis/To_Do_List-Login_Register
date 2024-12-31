package com.example.firstapplication.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.firstapplication.Models.Task;
import com.example.firstapplication.Repositories.TaskRepository;
import java.util.ArrayList;

public class TaskService {

    private final TaskRepository taskRepo;

    public TaskService(SQLiteDatabase db, Context context) {
        this.taskRepo = new TaskRepository(db, context); // Initialize TaskRepository with the context
    }

    public ArrayList<Task> tasks() {
        return taskRepo.getAllTasks(); // Load tasks from the database
    }

    public Task deleteTask(String deleteTask) {
        return taskRepo.deleteTask(deleteTask);
    }
}
