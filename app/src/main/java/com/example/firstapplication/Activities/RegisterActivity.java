package com.example.firstapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.AuthService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends BaseActivity {
    private EditText etRegEmail, etRegPassword;
    private Button btnRegister;
    private final AuthService authService = new AuthService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = etRegEmail.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                this.message(RegisterActivity.this, "Email and Password cannot be empty");
                return;
            }

            // Regex for email validation
            String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                this.message(RegisterActivity.this, "Invalid email format");
                return;
            }

            if (password.length() < 6) {
                this.message(RegisterActivity.this, "Password must be at least 6 characters long");
                return;
            }

            try {
                // Attempt registration
                if (authService.register(email, password) != null) {
                    this.message(RegisterActivity.this, "Registration Successful");

                    // Redirect to LoginActivity
                    startActivity(this.redirect(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    this.message(RegisterActivity.this, "Registration Failed. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                // Show validation error messages
                this.message(RegisterActivity.this, e.getMessage());
            } catch (Exception e) {
                // Handle unexpected errors
                this.message(RegisterActivity.this, "An error occurred. Please try again.");
            }
        });
    }
}