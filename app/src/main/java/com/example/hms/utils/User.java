package com.example.hms.utils;

public class User {
    public static final String TABLE_NAME = "user";
    public static final String KEY_ID = "user_id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ROLE = "role";

    public User( String full_name , String role) {

        this.full_name = full_name;
        this.role = role;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private int user_id;

    private String email;
    private String password;
    private String full_name;
    private String role;




    public User(int user_id, String email, String password, String full_name , String role) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.full_name = full_name;
        this.role = role ;
    }
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_EMAIL + " TEXT," +
            KEY_PASSWORD + " TEXT," +
            KEY_FULL_NAME + " TEXT," +  // Add a comma after the last non-role column definition
            KEY_ROLE + " TEXT" +  // Remove the extra closing parenthesis
            ");";


    public User() {
    }
}
