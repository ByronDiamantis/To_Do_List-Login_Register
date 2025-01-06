package com.example.firstapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstapplication.Models.User;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.AuthService;


public class LoginActivity extends BaseActivity {

    private AuthService authService;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = new AuthService(this);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Check if already logged in
        if (authService.isLoggedIn()) {
            // Redirect to MainActivity
            startActivity(this.redirect(LoginActivity.this, MainActivity.class));
            finish();
        }

        // Set event listeners
        btnLogin.setOnClickListener(v -> handleLogin());
        tvRegister.setOnClickListener(v -> {
            // Redirect to RegisterActivity
            startActivity(this.redirect(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            this.message(this, "Email and Password cannot be empty");
            return;
        }

        User user = authService.login(email, password);

        if (user != null) {
            this.message(this, "Login Successful");
            startActivity(this.redirect(this, MainActivity.class));
            finish();
        } else {
            this.message(this, "Invalid Credentials");
        }
    }
}

