package com.example.firstapplication.Activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.example.firstapplication.Models.Task;
import com.example.firstapplication.R;
import com.example.firstapplication.Adapters.TaskAdapter;
import com.example.firstapplication.Services.TaskService;


public class MainActivity extends BaseActivity {

    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private int userId; // User ID of the logged-in user



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  private final TaskService taskService = new TaskService(this);
        TaskService taskService = new TaskService(this);
        // Get the userId from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        // User ID of the logged-in user
        userId = preferences.getInt("userId", -1); // Default to -1 if not found

        if (userId != -1) {
            // Fetch tasks for the current user
            taskList = taskService.getTasksOfCurrentUser(); // Initialize task list and fetch tasks for the logged-in user
        } else {
            // Handle the case where no userId is found (e.g., user is not logged in)
            this.message(MainActivity.this, "Error: No user logged in.");
        }



        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList, taskService, userId);
        recyclerView.setAdapter(taskAdapter);


        // Set up Add Task button
        findViewById(R.id.addTaskButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1); // Start AddTaskActivity for result
        });

        // Reference to the Logout Button
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> logoutUser());

    }

    // Handles the process of receiving a task from AddTaskActivity and updating the task list in MainActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String taskName = data.getStringExtra("task"); // Get task name from intent

            if (taskName != null) {
                taskName = taskName.trim(); // Remove leading/trailing spaces
            }

            if (taskName != null && !taskName.isEmpty()) {
                // Generate a new ID for the task (if your app requires unique IDs)
                // Here, we assume IDs are auto-generated or not needed for local storage
                int newTaskId = taskList.size() + 1; // Example ID logic
                int userId = this.userId; // Use the current user's ID

                // Create a new Task object with all required fields
                Task newTask = new Task(newTaskId, userId, taskName);

                // Add the new Task to the list
                taskList.add(newTask);

                // Notify the adapter of the changes
                taskAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Task is empty!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void logoutUser() {
        // Reset login status
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}