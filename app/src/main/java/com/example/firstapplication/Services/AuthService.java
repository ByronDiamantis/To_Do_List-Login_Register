package com.example.firstapplication.Services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firstapplication.Models.User;
import com.example.firstapplication.Repositories.UserRepository;

public class AuthService {

    private final UserRepository userRepo;
    private final Context context;


    public AuthService(Context context) {
        // Initialize UserRepository with the context
        this.userRepo = new UserRepository(context);
        this.context = context;
    }
    public User login(String email, String password)
    {
        User user = userRepo.loginUser(email, password);

        if (user != null) {
            // TODO: store to sharedPref

            SharedPreferences sharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", user.getId());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            // Return instance of user
            return user;

        }
        return null;
    }
    public User register(String email, String password)
    {
        return userRepo.registerUser(email, password);
    }
}
