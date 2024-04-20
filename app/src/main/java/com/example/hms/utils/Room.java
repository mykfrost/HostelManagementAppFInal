package com.example.hms.utils;

import android.database.Cursor;

import com.example.hms.database.DatabaseHandler;

public class Room {
    private DatabaseHandler databaseHandler;

    public Room(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
    // Hostels table column names
    public static final String TABLE_NAME = "roomAllocation";
    public static final String KEY_ID = "room_id";
    public static final String KEY_STUDENT_ID = "student_id";
    public static  final String KEY_HOSTEL_ID = "hostel_id";
    public static final String KEY_ROOM_TYPE = "room_type";
    public static final String KEY_CAPACITY = "capacity";
    public static final String KEY_PRICE = "price";
    public static final String KEY_DESCRIPTION = "description";

    public Room(int room_id,int hostel_id,int student_id, String room_type, String capacity, int price, String description) {
        this.room_id = room_id;
        this.hostel_id = hostel_id;
        this.student_id = student_id;
        this.room_type = room_type;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
    }



    public Room() {
    }

public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME+
            "(" +
             KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_STUDENT_ID + " INTEGER," +
             KEY_HOSTEL_ID + " INTEGER," +
             KEY_ROOM_TYPE + " TEXT," +
             KEY_CAPACITY + " TEXT," +  // Can be changed to integer if capacity is a number
             KEY_PRICE + " TEXT," +
             KEY_DESCRIPTION + " TEXT," +
            "  FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES Students(KEY_ID)," +  // References Students table
            "  FOREIGN KEY(" + KEY_HOSTEL_ID + ") REFERENCES Hostels(KEY_ID)" +   // References Hostels table
            ")";




    public int getRoom_id() {
        return room_id;
    }
    public int getNumberOfRoomsInHostel(int hostel_id) {
        return databaseHandler.getHostelRoomsCount(hostel_id);
    }
    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public int room_id;
    public int hostel_id;
    public int  student_id;
    public String room_type;
    public String capacity;
    public int price;
    public String description;


}
