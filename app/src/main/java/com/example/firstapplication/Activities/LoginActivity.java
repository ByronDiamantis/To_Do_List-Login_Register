package com.example.firstapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.firstapplication.R;
import com.example.firstapplication.Services.AuthService;


public class LoginActivity extends BaseActivity {

    private final AuthService authService = new AuthService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Init variables to be used
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Event listeners
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();


            if(authService.login(email, password) != null) {
                
                this.message(LoginActivity.this, "Login Successful");

                // Redirect to MainActivity
                startActivity(this.redirect(LoginActivity.this, MainActivity.class));
                finish();
            }
            else{
                this.message(LoginActivity.this, "Invalid Credentials");
            }

        });

        tvRegister.setOnClickListener(v -> {
            // Redirect to RegisterActivity
            startActivity(this.redirect(LoginActivity.this, RegisterActivity.class));
        });
    }
}
