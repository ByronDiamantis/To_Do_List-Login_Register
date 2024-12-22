package com.example.firstapplication.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.firstapplication.DatabaseHelper;
import com.example.firstapplication.Models.User;


public class UserRepository extends DatabaseHelper {

    public UserRepository(Context context) {
        super(context); // Pass the context to the parent DatabaseHelper constructor
    }

    public long registerUser(String email, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);
        //return db.insert(DatabaseHelper.TABLE_USERS, null, values);
        return getWritableDatabase().insert(TABLE_USERS, null, values);
    }

    public User loginUser(String email, String password) {
        Cursor cursor = getReadableDatabase().query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{email, password},
                null, null, null
        );
        boolean isValid = cursor.moveToFirst(); // Check if a row exists

        if (cursor.moveToFirst()) {

            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD))
            );
            cursor.close();
            return user;
        }

        cursor.close();
        return null;
    }
}
