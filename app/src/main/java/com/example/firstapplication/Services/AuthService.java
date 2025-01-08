package com.example.firstapplication.Services;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.firstapplication.Models.User;
import com.example.firstapplication.Repositories.UserRepository;

public class AuthService {

    // SharedPreferences keys
    private static final String PREF_NAME = "user_pref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private final UserRepository userRepo;
    private final Context context;

    public AuthService(Context context) {
        // Initialize UserRepository with the context
        this.userRepo = new UserRepository(context);
        this.context = context;
    }

    // Register method
    public User register(String email, String password) {
        return userRepo.registerUser(email, password);
    }

    public User login(String email, String password) {

        User user = userRepo.loginUser(email, password);

        if (user != null) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_USER_ID, user.getId());
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.apply();

            // Return instance of user
            return user;
        }
        return null;
    }

    // Logout method
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply();
    }

    // Utility method to get the logged-in user ID
    public int getLoggedInUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    // Utility method to check login state
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
