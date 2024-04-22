package com.example.hms.utils;

public class Hostel {
    // Hostels table column names
    public static final String TABLE_NAME = "hostel";
    public static final String KEY_ID = "id";
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_NAME = "hostel_name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_IS_ADMIN = "is_admin";
    public static final String KEY_CAPACITY = "capacity"; // New field for capacity

    private int id;
    private int studentId;
    private String hostelName;
    private String description;
    private String address;
    private String city;
    private String country;
    private String email;
    private String fullName;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private boolean isAdmin;
    private int capacity; // Capacity field
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Use INTEGER PRIMARY KEY for AUTOINCREMENT
            KEY_STUDENT_ID + " INTEGER UNSIGNED," + // Correct data type
            KEY_NAME + " TEXT NOT NULL," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_ADDRESS + " TEXT," +
            KEY_CITY + " TEXT," +
            KEY_COUNTRY + " TEXT," +
            KEY_FULL_NAME + " TEXT," +
            KEY_IS_ADMIN + " INTEGER DEFAULT 0," +
            KEY_CAPACITY + " INTEGER UNSIGNED," + // Correct data type
            "FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.KEY_ID + "))";


//    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
//            "(" + KEY_ID + " INT UNSIGNED PRIMARY KEY AUTOINCREMENT," + // Match data type and constraints
//            KEY_STUDENT_ID + " INT UNSIGNED," + // Match data type and constraints
//            KEY_NAME + " TEXT NOT NULL," +
//            KEY_DESCRIPTION + " TEXT," +
//            KEY_ADDRESS + " TEXT," +
//            KEY_CITY + " TEXT," +
//            KEY_COUNTRY + " TEXT," +
//            KEY_FULL_NAME + " TEXT," +
//            KEY_IS_ADMIN + " INTEGER DEFAULT 0," + // Match default value
//            KEY_CAPACITY + " INT UNSIGNED," + // Add capacity field
//            "FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.KEY_ID + "))"; // Match foreign key reference

    public Hostel() {
    }

    public Hostel(int id, int studentId, String hostelName, String description, String address, String city, String country, String fullName, boolean isAdmin, int capacity) {
        this.id = id;
        this.studentId = studentId;
        this.hostelName = hostelName;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
        this.capacity = capacity; // Initialize capacity
    }

   //hostelname, description, address, city, country, fullName,isAdmin,capacity
    public Hostel( String hostelName, String description, String address, String city, String country, String fullName, boolean isAdmin, int capacity) {
        this.hostelName = hostelName;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
        this.capacity = capacity; // Initialize capacity
    }

    // Getter and setter methods for capacity
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
