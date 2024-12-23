package com.example.firstapplication.Activities;

import static android.content.ContentValues.TAG;
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
import com.example.firstapplication.Database.DatabaseHelper;
import com.example.firstapplication.R;
import com.example.firstapplication.Repositories.TaskRepository;
import com.example.firstapplication.Adapters.TaskAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<String> taskList;
    private TaskRepository taskRepo;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        taskRepo = new TaskRepository(db, this);

        taskList = new ArrayList<>();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = taskRepo.getAllTasks(); // Load tasks from the database
        Log.d(TAG, "Task list" + taskList);

        // Set up the RecyclerView adapter
        taskAdapter = new TaskAdapter(taskList, taskRepo);
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

     // Debug Log
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newTask = data.getStringExtra("task");
            taskList.add(newTask); // Add task to list
            taskAdapter.notifyDataSetChanged(); // Notify the adapter
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