package com.example.firstapplication.Activities;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.example.firstapplication.Database.DatabaseHelper;
import com.example.firstapplication.Models.Task;
import com.example.firstapplication.R;
import com.example.firstapplication.Adapters.TaskAdapter;
import com.example.firstapplication.Services.TaskService;


public class MainActivity extends AppCompatActivity {

    private TaskService taskService;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database and TaskService
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        taskService = new TaskService(db, this);

        taskList = new ArrayList<>();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = taskService.tasks(); // Fetch tasks from the service

        // Set up the RecyclerView adapter
        taskAdapter = new TaskAdapter(taskList, taskService);
        recyclerView.setAdapter(taskAdapter);

        // Reference to the Logout Button
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> logoutUser());

        // Handle adding a new task
        findViewById(R.id.addTaskButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    // Handles the process of receiving a task from AddTaskActivity and updating the task list in MainActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("MainActivity", "onActivityResult called with requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Log.d("MainActivity", "Intent data: " + data); // Debugging log
            String taskName = data.getStringExtra("task"); // Get task from intent
            Log.d("MainActivity", "Task name received: " + taskName);

            if (taskName != null) {
                taskName = taskName.trim(); // Remove leading/trailing spaces
            }

            if (taskName != null && !taskName.isEmpty()) {
                Log.d("MainActivity", "Adding task: " + taskName);
                Task newTask = new Task(taskName); // Create Task object
                taskList.add(newTask); // Add to list
                Log.d("MainActivity", "Task added successfully: " + newTask.getTask());
                taskAdapter.notifyDataSetChanged(); // Refresh RecyclerView
            } else {
                Log.d("MainActivity", "Invalid or empty task name");
                Toast.makeText(this, "Task is empty!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("MainActivity", "No data received or result not OK");
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