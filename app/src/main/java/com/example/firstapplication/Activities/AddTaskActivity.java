package com.example.firstapplication.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.EditText;
import com.example.firstapplication.R;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskEditText = findViewById(R.id.taskEditText);
        findViewById(R.id.saveTaskButton).setOnClickListener(v -> {
            String task = taskEditText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("task", task);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}