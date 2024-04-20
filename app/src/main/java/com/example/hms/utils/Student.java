package com.example.hms.utils;

public class Student {

    public static final String TABLE_NAME = "students";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "student_name";
    public static final String KEY_EMAIL = "student_email";
    public static final String KEY_REGISTRATION = "registration_no";
    public static final String KEY_GUARDIAN = "guardian";
    public static final String KEY_PHONE = "phone";
    public Student() {
    }

    public Student(int id, String student_name,String student_email, String registration_no, String guardian, String phone) {
        this.id = id;
        this.student_name = student_name;
        this.registration_no = registration_no;
        this.guardian = guardian;
        this.phone = phone;
    }

//    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
//            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            KEY_NAME + " TEXT," +
//            KEY_EMAIL + " TEXT," +
//            KEY_REGISTRATION + " TEXT," +
//            KEY_GUARDIAN + " TEXT," +
//            KEY_PHONE + " TEXT," +
//            /* No foreign key needed here for this relationship */
//            ")";
public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        KEY_NAME + " TEXT," +
        KEY_EMAIL + " TEXT," +
        KEY_REGISTRATION + " TEXT," +
        KEY_GUARDIAN + " TEXT," +
        KEY_PHONE + " TEXT" +
        ");";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private  int id;
    private  String student_name;
    private  String registration_no;
    private  String guardian;
    private  String  phone;
}
