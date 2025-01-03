package com.example.firstapplication.Services;

import android.content.Context;

import com.example.firstapplication.Models.Task;
import com.example.firstapplication.Repositories.TaskRepository;
import java.util.ArrayList;


// Encapsulate the logic for managing tasks
public class TaskService {

    private final TaskRepository taskRepo;

    public TaskService(Context context) {
        this.taskRepo = new TaskRepository(context); // Initialize TaskRepository with context
    }

    // Retrieve all tasks for a specific user
    public ArrayList<Task> getTasksOfCurrentUser(int userId) {
        return taskRepo.getTasksOfCurrentUser(userId); // Pass userId to fetch tasks for the logged-in user
    }

    // Add a task for the specific user
    public void addTask(String taskName, int userId) {
        taskRepo.addTask(taskName, userId);
    }

    // Delete a task for the specific user
    public void deleteTask(String taskName, int userId) {
        taskRepo.deleteTask(taskName, userId);
    }
}
