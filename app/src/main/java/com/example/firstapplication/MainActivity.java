package com.example.firstapplication;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<String> taskList;
    public DatabaseHelper dbHelper;
    public TaskRepository taskRepository;
    public SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        //DatabaseHelper dbHelper = new DatabaseHelper(this);

        //TaskRepository taskRepository = new TaskRepository(db);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load tasks from the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        TaskRepository taskRepositoy = new TaskRepository(db);
//        taskRepositoy.getAllTasks();

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        taskRepository = new TaskRepository(db);


        // Set up the RecyclerView adapter
        taskAdapter = new TaskAdapter(taskList, dbHelper);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newTask = data.getStringExtra("task");

            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getWritableDatabase();
            taskRepository = new TaskRepository(db);
            taskRepository.insertTask(newTask); // Add task to database

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