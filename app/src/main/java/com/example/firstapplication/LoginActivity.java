package com.example.firstapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    public DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
//        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
//        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
//
//        if (isLoggedIn) {
//            // If logged in, go to MainActivity directly
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//            return; // Exit onCreate() early
//        }

        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        Log.d(TAG, "Context in LoginActivity: " + this);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (dbHelper != null && dbHelper.validateUser(email, password)) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Redirect to MainActivity
                Log.d(TAG, "Navigating to MainActivity");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Log.d(TAG, "Intent data" + intent);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegister.setOnClickListener(v -> {
            // Redirect to RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
