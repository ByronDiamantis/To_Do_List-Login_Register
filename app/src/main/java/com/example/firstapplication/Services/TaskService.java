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
        try {
            return taskRepo.getTasksOfCurrentUser(userId); // Pass userId to fetch tasks for the logged-in user
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Add a task for the logged-in user
    public boolean addTask(String taskName) {
        int userId = authService.getLoggedInUserId(); // Utility method to get the logged-in user ID
        if (userId != -1) {
            try {
                taskRepo.addTask(taskName, userId);
                return true; // Task successfully added
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Task addition failed
            }
        } else {
            throw new IllegalStateException("No logged-in user found");
        }
    }


    // Delete a task for the logged-in user
    public boolean deleteTask(String taskName) {
        int userId = authService.getLoggedInUserId(); // Utility method to get the logged-in user ID
        if (userId != -1) {
            try {
                taskRepo.deleteTask(taskName, userId);
                return true; // Deletion successful
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Deletion failed
            }
        } else {
            throw new IllegalStateException("No logged-in user found");
        }
    }

}
