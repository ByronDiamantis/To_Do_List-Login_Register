package com.example.firstapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.firstapplication.Models.User;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.AuthService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends BaseActivity {

    private AuthService authService;
    private EditText etRegEmail, etRegPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authService = new AuthService(this);

        // Initialize views
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        // Set event listener
        btnRegister.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        String email = etRegEmail.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            this.message(this, "Email and Password cannot be empty");
            return;
        }

        if (!isValidEmail(email)) {
            this.message(this, "Invalid email format");
            return;
        }

        if (password.length() < 6) {
            this.message(this, "Password must be at least 6 characters long");
            return;
        }

        User user = authService.register(email, password);

        if (user != null) {
            this.message(this, "Registration Successful");
            startActivity(this.redirect(this, LoginActivity.class));
            finish();
        } else {
            this.message(this, "Registration Failed. Email may already be in use.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }
}
