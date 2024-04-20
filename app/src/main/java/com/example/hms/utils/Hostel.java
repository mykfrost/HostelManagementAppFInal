package com.example.hms.utils;

public class Hostel {
    // Hostels table column names
    public static final String TABLE_NAME = "hostels";
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
    private int isAdmin;
    private int capacity; // Capacity field

    // SQL statement to create the hostels table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_STUDENT_ID + " INTEGER," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_ADDRESS + " TEXT," +
            KEY_CITY + " TEXT," +
            KEY_COUNTRY + " TEXT," +
            KEY_EMAIL + " TEXT," +
            KEY_FULL_NAME + " TEXT," +
            KEY_IS_ADMIN + " INTEGER," +
            KEY_CAPACITY + " INTEGER," + // Added capacity field to the table schema
            "FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + KEY_ID + "))";

    public Hostel() {
    }

    public Hostel(int id, int studentId, String hostelName, String description, String address, String city, String country, String email, String fullName, int isAdmin, int capacity) {
        this.id = id;
        this.studentId = studentId;
        this.hostelName = hostelName;
        this.description = description;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
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

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getNumberOfRooms() {
        // Assuming id is the hostel_id, you can modify this logic based on your actual implementation
        Room roomInstance = new Room();
        return roomInstance.getNumberOfRoomsInHostel(this.id);
    }
}
