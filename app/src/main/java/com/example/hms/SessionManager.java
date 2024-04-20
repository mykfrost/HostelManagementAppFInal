package com.example.hms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.hms.utils.User;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "user_role"; // Added user role key
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String ROLE_ADMIN = "admin"; // Added role constants
    private static final String ROLE_USER = "user"; // Added role constants

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(User user) {
//        editor.putString(KEY_EMAIL, email);
//        editor.putString(KEY_FULL_NAME, fullName);
//        editor.putBoolean(KEY_IS_LOGGED_IN, true);
//        editor.apply();

        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FULL_NAME, user.getFull_name());
        editor.putString(KEY_ROLE, user.getRole()); // Store role information
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }
    public void setAdmin(boolean isAdmin) {
        editor.putString(KEY_ROLE, isAdmin ? ROLE_ADMIN : ROLE_USER);
        editor.apply();
    }

    public boolean isAdmin() {
        return pref.getString(KEY_ROLE, ROLE_USER).equals(ROLE_ADMIN);
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getFullName() {
        return pref.getString(KEY_FULL_NAME, null);
    }
}