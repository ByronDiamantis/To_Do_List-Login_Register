package com.example.firstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class UserRepository extends DatabaseHelper{

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

    public boolean validateUser(String email, String password) {
        Cursor cursor = getReadableDatabase().query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?",
                new String[]{email, password},
                null, null, null
        );
        boolean isValid = cursor.moveToFirst(); // Check if a row exists
        cursor.close();
        return isValid;



    }
}
