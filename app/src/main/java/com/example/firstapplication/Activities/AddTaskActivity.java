package com.example.firstapplication.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.TaskService;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskEditText;
    private TaskService taskService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskService = new TaskService(this);
        taskEditText = findViewById(R.id.taskEditText);

        findViewById(R.id.saveTaskButton).setOnClickListener(v -> {
            String taskName = taskEditText.getText().toString().trim();

            if (taskName.isEmpty()) {
                Toast.makeText(this, "Task cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {
                if (taskService.addTask(taskName)) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("task", taskName);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}