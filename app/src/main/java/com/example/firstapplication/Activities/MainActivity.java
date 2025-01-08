package com.example.firstapplication.Activities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import com.example.firstapplication.Models.Task;
import com.example.firstapplication.R;
import com.example.firstapplication.Adapters.TaskAdapter;
import com.example.firstapplication.Services.AuthService;
import com.example.firstapplication.Services.TaskService;


public class MainActivity extends BaseActivity {
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private int userId; // User ID of the logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskService taskService = new TaskService(this);
        AuthService authService = new AuthService(this);

        // Fetch the logged-in user's ID
        userId = authService.getLoggedInUserId();

        if (userId == -1) {
            // Redirect to LoginActivity if user is not logged in
            startActivity(this.redirect(this, LoginActivity.class));
            finish();
            return;
        }

        taskList = taskService.getTasksOfCurrentUser(); // Initialize task list and fetch tasks for the logged-in user

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList, taskService, userId);
        recyclerView.setAdapter(taskAdapter);

        // Set up Add Task button
        findViewById(R.id.addTaskButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });

        // Set up Logout button
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            authService.logout(); // Use AuthService for logout
            startActivity(this.redirect(this, LoginActivity.class));
            finish();
        });
    }

    // Handles adding tasks from AddTaskActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TaskService taskService = new TaskService(this);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String taskName = data.getStringExtra("task");

            if (taskName != null && !taskName.trim().isEmpty()) {
                taskList.clear();
                taskList.addAll(taskService.getTasksOfCurrentUser()); // Refresh tasks
                taskAdapter.notifyDataSetChanged(); // Notify the adapter
            } else {
                this.message(this, "Task is empty!");
            }
        }
    }
}