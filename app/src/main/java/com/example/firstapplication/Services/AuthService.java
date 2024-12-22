package com.example.firstapplication.Services;

import android.content.Context;

import com.example.firstapplication.Models.User;
import com.example.firstapplication.Repositories.UserRepository;

public class AuthService {

    private final UserRepository userRepo;

    public AuthService(Context context) {
        // Initialize UserRepository with the context
        this.userRepo = new UserRepository(context);
    }
    public User login(String email, String password)
    {
        // Validate if email is actually an email.
        // Validate if password is greater that 6 characters.

        return userRepo.loginUser(email, password);
    }
    public long register(String email, String password)
    {
        return userRepo.registerUser(email, password);
    }
}
