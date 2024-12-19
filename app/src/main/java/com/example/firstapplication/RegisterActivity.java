package com.example.firstapplication;

import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    private EditText etRegEmail, etRegPassword;
    private Button btnRegister;
    public DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        Log.d(TAG, "onCreate: RegisterActivity initialized"); // Debug Log

        dbHelper = new DatabaseHelper(this);
        Log.d(TAG, dbHelper.toString()); // Debug Log

        btnRegister.setOnClickListener(v -> {
            String email = etRegEmail.getText().toString();
            String password = etRegPassword.getText().toString();

            Log.d(TAG, email); // Debug Log
            Log.d(TAG, password); // Debug Log
            Log.d(TAG, "Btn clicked"); // Debug Log

            // long result = dbHelper.registerUser(email, password);

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                long result = dbHelper.registerUser(email, password);

                if (result > 0) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    //handleRegisterSuccess();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
