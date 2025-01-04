package com.example.firstapplication.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.firstapplication.Database.DatabaseHelper;
import com.example.firstapplication.Models.User;

public class UserRepository extends DatabaseHelper {

    public UserRepository(Context context) {
        super(context); // Pass the context to the parent DatabaseHelper constructor
    }

    public User registerUser(String email, String password) {

        // Check if the email already exists
        Cursor cursor = getReadableDatabase().query(
                DatabaseHelper.TABLE_USERS,
                new String[]{DatabaseHelper.COLUMN_USER_EMAIL},
                DatabaseHelper.COLUMN_USER_EMAIL + " = ?",
                new String[]{email},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return null; // Email already exists
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);

        // Insert the new row and get its ID
        long rowId = getWritableDatabase().insert(DatabaseHelper.TABLE_USERS, null, values);

        // Check if the insertion was successful
        if (rowId != -1) {
            // Return a new User object with the ID, email, and password
            return new User((int) rowId, email, password);
        } else {
            return null; // Handle failure (optional: throw an exception or return null)
        }
    }

    public User loginUser(String email, String password) {
        Cursor cursor = getReadableDatabase().query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD},
                COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{email, password},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) { // Ensure cursor is not null
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD))
            );
            cursor.close();
            return user;
        }

        // Close cursor if it was created but has no rows
        if (cursor != null) {
            cursor.close();
        }
        return null; // Return null if no user is found
    }
}
