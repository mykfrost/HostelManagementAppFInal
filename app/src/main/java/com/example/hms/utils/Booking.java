package com.example.hms.utils;

public class Booking {
    public static final String TABLE_NAME = "bookings";
    public static final String KEY_ID = "id";
    public static final String KEY_ROOM_ID = "room_id";
    public static final String KEY_HOSTEL_ID = "hostel_id"; // Added hostel_id
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_CHECK_IN_DATE = "check_in_date";
    public static final String KEY_CHECK_OUT_DATE = "check_out_date";
    public static final String KEY_TOTAL_PRICE = "total_price";
    public static final String KEY_ROOM_TYPE = "room_type";

    private int id;
    private int roomId;
    private int hostelId; // Added hostelId
    private int studentId;
    private String checkInDate;
    private String checkOutDate;
    private double totalPrice;
    private String roomType; // Added roomType

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_ROOM_ID + " INTEGER," +
            KEY_HOSTEL_ID + " INTEGER," + // Added hostel_id to CREATE_TABLE
            KEY_STUDENT_ID + " INTEGER," +
            KEY_ROOM_TYPE + " TEXT," + // Added roomType to CREATE_TABLE
            KEY_CHECK_IN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            KEY_CHECK_OUT_DATE + " TEXT," +
            KEY_TOTAL_PRICE + " DECIMAL(10,2)," +
            "FOREIGN KEY(" + KEY_ROOM_ID + ") REFERENCES " + Room.TABLE_NAME + "(" + Room.KEY_ID + ")," +
            "FOREIGN KEY(" + KEY_HOSTEL_ID + ") REFERENCES " + Hostel.TABLE_NAME + "(" + Hostel.KEY_ID + ")," + // Added FOREIGN KEY constraint for hostel_id
            "FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES " + Student.TABLE_NAME + "(" + Student.KEY_ID + "))";

    public Booking() {
    }

    public Booking(int id, int roomId, int hostelId, int studentId, String checkInDate, String checkOutDate, double totalPrice, String roomType) {
        this.id = id;
        this.roomId = roomId;
        this.hostelId = hostelId;
        this.studentId = studentId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.roomType = roomType;
    }

    // Getters and setters for private fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
