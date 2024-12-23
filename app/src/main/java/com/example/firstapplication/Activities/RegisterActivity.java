package com.example.firstapplication.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.example.firstapplication.R;
import com.example.firstapplication.Services.AuthService;


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
            String email = etRegEmail.getText().toString();
            String password = etRegPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                this.message(RegisterActivity.this, "Email and Password cannot be empty");

            } else {

                if (authService.register(email, password) != null) {
                    this.message(RegisterActivity.this, "Registration Successful");
                } else {
                    this.message(RegisterActivity.this, "Registration Failed");
                }
            }
        });
    }
}
