package com.example.firstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    private EditText etRegEmail, etRegPassword;
    private Button btnRegister;
    private UserRepository userRepo;
//    public DatabaseHelper dbHelper;
//    public SQLiteDatabase db;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        userRepo = new UserRepository(context);

        btnRegister.setOnClickListener(v -> {
            String email = etRegEmail.getText().toString();
            String password = etRegPassword.getText().toString();

            long result = userRepo.registerUser(email, password);

            if (result > 0) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                handleRegisterSuccess();
                // Redirect to LoginActivity
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //When a user successfully registers, you can optionally log them in directly after registration
    private void handleRegisterSuccess() {
        SharedPreferences preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();

        // Redirect to MainActivity
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
