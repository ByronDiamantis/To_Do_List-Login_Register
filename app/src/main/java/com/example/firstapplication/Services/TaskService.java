package com.example.firstapplication.Services;

import android.content.Context;

import com.example.firstapplication.Models.Task;
import com.example.firstapplication.Repositories.TaskRepository;
import java.util.ArrayList;


// Encapsulate the logic for managing tasks
public class TaskService {

    private final TaskRepository taskRepo;
    private final AuthService authService;

    public TaskService(Context context) {
        this.taskRepo = new TaskRepository(context); // Initialize TaskRepository with context
        this.authService = new AuthService(context); // Initialize AuthService to access user session
    }

    // Retrieve all tasks for a specific user
    public ArrayList<Task> getTasksOfCurrentUser() {
        int userId = authService.getLoggedInUserId(); // Utility method to get the logged-in user ID
        if (userId == -1) {
            return new ArrayList<>(); // Return an empty list if no user is logged in
        }
        return taskRepo.getTasksOfCurrentUser(userId); // Pass userId to fetch tasks for the logged-in user
    }

    // Add a task for the logged-in user
    public void addTask(String taskName) {
        int userId = authService.getLoggedInUserId(); // Utility method to get the logged-in user ID
        if (userId != -1) {
            taskRepo.addTask(taskName, userId);
        } else {
            throw new IllegalStateException("No logged-in user found");
        }
    }

    // Delete a task for the logged-in user
    public void deleteTask(String taskName) {
        int userId = authService.getLoggedInUserId(); // Utility method to get the logged-in user ID
        if (userId != -1) {
            taskRepo.deleteTask(taskName, userId);
        } else {
            throw new IllegalStateException("No logged-in user found");
        }
    }
}
