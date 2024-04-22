package com.example.hms.utils;

import android.database.Cursor;

import com.example.hms.database.DatabaseHandler;

public class Room {
    private DatabaseHandler databaseHandler;

    //public Room(DatabaseHandler databaseHandler) {
//        this.databaseHandler = databaseHandler;
//    }
    // Hostels table column names
    // Room table column names
    public static final String TABLE_NAME = "room";
    public static final String KEY_ID = "id";
    public static final String KEY_HOSTEL_ID = "hostel_id";
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_ROOM_TYPE = "room_type";
    public static final String KEY_CAPACITY = "capacity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATUS = "status";
    public static final String KEY_PRICE_PER_NIGHT = "price_per_night";
    public static final String KEY_BOOKING_DATE = "booking_date";



    public Room(int id, int hostel_id, int student_id, String room_type, String capacity, int price, String description, String status, double price_per_night, String booking_date) {
        this.id = id;
        this.hostel_id = hostel_id;
        this.student_id = student_id;
        this.room_type = room_type;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
        this.status = status;
        this.price_per_night = price_per_night;
        this.booking_date = booking_date;
    }

    public Room() {
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_HOSTEL_ID + " INTEGER," +
            KEY_STUDENT_ID + " INTEGER," +
            KEY_ROOM_TYPE + " TEXT," +
            KEY_CAPACITY + " TEXT," +
            KEY_PRICE + " INTEGER," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_STATUS + " TEXT DEFAULT 'available'," +
            KEY_PRICE_PER_NIGHT + " DECIMAL(10,2) NOT NULL," +
            KEY_BOOKING_DATE + " DATE" +
            ")";

    public int id;
    public int hostel_id;
    public int student_id;
    public String room_type;
    public String capacity;
    public int price;
    public String description;
    public String status;
    public double price_per_night;
    public String booking_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(int hostel_id) {
        this.hostel_id = hostel_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(double price_per_night) {
        this.price_per_night = price_per_night;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

//    public int getNumberOfRoomsInHostel(int hostel_id) {
//        return databaseHandler.getHostelRoomsCount(hostel_id);
//    }


}
