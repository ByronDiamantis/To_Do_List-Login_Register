package com.example.firstapplication.Activities;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public void message(Activity activity, String message)
    {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public Intent redirect(Activity from , Class<?> to)
    {
        return new Intent(from, to);
    }
}
