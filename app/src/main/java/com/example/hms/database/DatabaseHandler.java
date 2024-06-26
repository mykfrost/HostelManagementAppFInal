package com.example.hms.database;
import static com.example.hms.utils.Hostel.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.example.hms.SessionManager;
import com.example.hms.Singleton.VolleySingleton;
import com.example.hms.utils.Booking;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.Notification;
import com.example.hms.utils.Room;
import com.example.hms.utils.Student;
import com.example.hms.utils.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final SessionManager sessionManager;
    public VolleySingleton singleton ;
    private RequestQueue requestQueue;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lhxslmzq_android";



    public DatabaseHandler(@Nullable Context context, SessionManager sessionManager) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.sessionManager = sessionManager;
        singleton = VolleySingleton.getInstance(context);
        requestQueue = singleton.getRequestQueue();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Hostel.CREATE_TABLE);
        db.execSQL(Room.CREATE_TABLE);
        db.execSQL(Student.CREATE_TABLE);
        db.execSQL(Booking.CREATE_TABLE);
        db.execSQL(Notification.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Hostel.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Room.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Booking.TABLE_NAME);

        // Create tables again
        onCreate(db);    // Drop older table if existed

    }
// Upload ALl Tables to  server

//    public void uploadDatabaseToServer() {
//        String url = "https://pmenergies.co.ke/android/upload_database.php"; // Replace with your server endpoint URL
//
//        // Get writable database
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Query to fetch all data from all tables
//        String selectQuery = "SELECT * FROM " + User.TABLE_NAME +
//                " UNION ALL SELECT * FROM " + Hostel.TABLE_NAME +
//                " UNION ALL SELECT * FROM " + Room.TABLE_NAME +
//                " UNION ALL SELECT * FROM " + Student.TABLE_NAME +
//                " UNION ALL SELECT * FROM " + Booking.TABLE_NAME+
//                " UNION ALL SELECT * FROM " + Notification.TABLE_NAME;
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // Convert cursor data to JSON
//        JSONArray jsonArray = new JSONArray();
//        if (cursor.moveToFirst()) {
//            do {
//                int columns = cursor.getColumnCount();
//                JSONObject jsonObject = new JSONObject();
//                for (int i = 0; i < columns; i++) {
//                    String columnName = cursor.getColumnName(i);
//                    String columnValue = cursor.getString(i);
//                    try {
//                        jsonObject.put(columnName, columnValue);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                jsonArray.put(jsonObject);
//            } while (cursor.moveToNext());
//        }
//
//        // Close cursor and database
//        cursor.close();
//        db.close();
//
//        // Create a JSON object with the data array
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("data", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Create a new JsonObjectRequest using POST method
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestData,
//                response -> {
//                    // Handle the server response here
//                    try {
//                        String message = response.getString("message");
//                        int status = response.getInt("status");
//                        if (status == 0) {
//                            // Database uploaded successfully
//                            Log.d("UploadDatabase", message);
//                        } else {
//                            // Error uploading database
//                            Log.e("UploadDatabase", message);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> {
//                    // Handle Volley errors here
//                    Log.e("UploadDatabase", "Volley Error: " + error.toString());
//                });
//
//        // Add the request to the RequestQueue
//        requestQueue.add(request);
//    }

    // Get User from user database
    @SuppressLint("Range")
    public void getUserlById(User userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + User.TABLE_NAME + " WHERE " +
                User.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(User.KEY_ID)));
        }

        cursor.close();
        db.close();
    }

    // Add notifications
//    public long addNotification(int hostelId, int senderId, int receiverId, String message) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selectQuery = "SELECT * FROM " + Notification.TABLE_NAME + " WHERE " +
//                Notification.KEY_ID + " = ?";
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(message)});
//        ContentValues values = new ContentValues();
//        values.put(Notification.KEY_HOSTEL_ID, hostelId);
//        values.put(Notification.KEY_SENDER_ID, senderId);
//        values.put(Notification.KEY_RECEIVER_ID, receiverId);
//        values.put(Notification.KEY_MESSAGE, message);
//
//        long id = db.insert(Notification.TABLE_NAME, null, values);
//
//        db.close();
//
//        return id;
//    }
    public long addNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + Notification.TABLE_NAME + " WHERE " +
                Notification.KEY_MESSAGE + " = ? AND " +
                Notification.KEY_SENDER_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{notification.getMessage(), String.valueOf(notification.getSenderId())});

        // Check if a notification with the same message and sender ID exists
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            // Handle the scenario where the notification already exists
            return -1; // You can return a specific value to indicate the notification already exists
        }

        // If the notification doesn't exist, proceed with inserting a new one
        ContentValues values = new ContentValues();
       // values.put(Notification.KEY_HOSTEL_ID, hostelId);
        values.put(Notification.KEY_SENDER_ID, notification.getSenderId());
        values.put(Notification.KEY_RECEIVER_ID,notification.getReceiverId());
        values.put(Notification.KEY_MESSAGE, notification.getMessage());

        long id = db.insert(Notification.TABLE_NAME, null, values);

        db.close();

        return id;
    }
    // Get all notifications
    public List<Notification> getAllNotifications() {
        List<Notification> notificationList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Notification.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Notification.KEY_ID));
               // int hostelId = cursor.getInt(cursor.getColumnIndex(Notification.KEY_HOSTEL_ID));
                @SuppressLint("Range") int senderId = cursor.getInt(cursor.getColumnIndex(Notification.KEY_SENDER_ID));
                @SuppressLint("Range") int receiverId = cursor.getInt(cursor.getColumnIndex(Notification.KEY_RECEIVER_ID));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex(Notification.KEY_MESSAGE));
                @SuppressLint("Range") String sentAt = cursor.getString(cursor.getColumnIndex(Notification.KEY_SENT_AT));

                Notification notification = new Notification(id,  senderId, receiverId, message, sentAt);
                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notificationList;
    }


    //Get notification
    @SuppressLint("Range")
    public Notification getNotification(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Notification.TABLE_NAME,
                new String[]{Notification.KEY_ID,
                        Notification.KEY_HOSTEL_ID,
                        Notification.KEY_SENDER_ID,
                        Notification.KEY_RECEIVER_ID,
                        Notification.KEY_MESSAGE},
                Notification.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Notification notification = null;
        if (cursor != null) {
            notification = new Notification(
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_ID)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_HOSTEL_ID)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_SENDER_ID)),
                    cursor.getInt(cursor.getColumnIndex(Notification.KEY_RECEIVER_ID)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_MESSAGE)),
                    cursor.getString(cursor.getColumnIndex(Notification.KEY_SENT_AT)));
        }

        if (cursor != null) {
            cursor.close();
        }

        return notification;
    }

    public long addStudent( String student_name,String student_email , String registration_no, String guardian, String phone) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Student.KEY_NAME, student_name);
        values.put(Student.KEY_EMAIL, student_email);
        values.put(Student.KEY_REGISTRATION, registration_no);
        values.put(Student.KEY_GUARDIAN, guardian);
        values.put(Student.KEY_PHONE, phone);

        // insert row
        long id = db.insert(Student.TABLE_NAME, null, values);

        // close db connection
        db.close();


        return id;
    }
    //Get all students

    @SuppressLint("Range")
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Student.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(Student.KEY_ID)));
                student.setStudent_name(cursor.getString(cursor.getColumnIndex(Student.KEY_NAME)));
                student.setEmail(cursor.getString(cursor.getColumnIndex(Student.KEY_EMAIL)));
                student.setRegistration_no(cursor.getString(cursor.getColumnIndex(Student.KEY_REGISTRATION)));
                student.setGuardian(cursor.getString(cursor.getColumnIndex(Student.KEY_GUARDIAN)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(Student.KEY_PHONE)));

                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return students;
    }

// Get a student

    @SuppressLint("Range")
    public Student getStudentById(int studentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Student.TABLE_NAME + " WHERE " +
                Student.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(studentId)});

        Student student = null;
        if (cursor.moveToFirst()) {
            student = new Student();
            student.setId(cursor.getInt(cursor.getColumnIndex(Student.KEY_ID)));
            student.setStudent_name(cursor.getString(cursor.getColumnIndex(Student.KEY_NAME)));
            student.setEmail(cursor.getString(cursor.getColumnIndex(Student.KEY_EMAIL)));
            student.setRegistration_no(cursor.getString(cursor.getColumnIndex(Student.KEY_REGISTRATION)));
            student.setGuardian(cursor.getString(cursor.getColumnIndex(Student.KEY_GUARDIAN)));
            student.setPhone(cursor.getString(cursor.getColumnIndex(Student.KEY_PHONE)));
        }

        cursor.close();
        db.close();

        return student;
    }


    public long addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Room.KEY_ROOM_TYPE, room.getRoom_type());
        values.put(Room.KEY_STATUS , room.getStatus());
       values.put(Room.KEY_CAPACITY, room.getCapacity());
      values.put(Room.KEY_PRICE, room.getPrice());
      values.put(Room.KEY_DESCRIPTION, room.getDescription());
       values.put(Room.KEY_HOSTEL_ID, room.getHostel_id());

        long roomId = db.insert(Room.TABLE_NAME, null, values);
        db.close();
        return roomId;
    }

public long addHostel(String hostel_name, String description, String address,
                      String city, String country, String fullName,boolean isAdmin , int capacity) {

    // Get writable database
    SQLiteDatabase db = this.getWritableDatabase();

    // Create ContentValues to store hostel data
    ContentValues values = new ContentValues();
    values.put(Hostel.KEY_NAME, hostel_name);
    values.put(Hostel.KEY_DESCRIPTION, description);
    values.put(Hostel.KEY_ADDRESS, address);
    values.put(Hostel.KEY_CITY, city);
    values.put(Hostel.KEY_COUNTRY, country);
    values.put(Hostel.KEY_FULL_NAME, fullName);
    values.put(Hostel.KEY_IS_ADMIN,isAdmin ? 1 : 0);
    values.put(Hostel.KEY_CAPACITY, capacity);


    // Insert hostel data in the table
    long hostelId = db.insert(Hostel.TABLE_NAME, null, values);

    // Close the database connection
    db.close();

    // Return the newly inserted hostel's ID
    return hostelId;
}
    public long addBooking(int room_id , int hostel_id , String hostel_name , int student_id ,
                           String studentName, String roomType ,

                           String check_in,String check_out , double price) {


        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Booking.KEY_ROOM_ID , room_id);
        values.put(Booking.KEY_HOSTEL_ID, hostel_id);
        values.put(Booking.KEY_HOSTEL_NAME,hostel_name);
        values.put(Booking.KEY_STUDENT_ID, student_id);
        values.put(Booking.KEY_STUDENT_NAME,studentName);
        values.put(Booking.KEY_ROOM_TYPE, roomType);
        values.put(Booking.KEY_CHECK_IN_DATE, check_in);
        values.put(Booking.KEY_CHECK_OUT_DATE, check_out);
        values.put(Booking.KEY_TOTAL_PRICE, price);

        // insert row
        long id = db.insert(Booking.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    @SuppressLint("Range")
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Booking.TABLE_NAME + " ORDER BY " +
                Booking.KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndex(Booking.KEY_ID)));
                booking.setRoomId(cursor.getInt(cursor.getColumnIndex(Booking.KEY_ROOM_ID)));
                booking.setHostelId(cursor.getInt(cursor.getColumnIndex(Booking.KEY_HOSTEL_ID)));
                booking.setStudentId(cursor.getInt(cursor.getColumnIndex(Booking.KEY_STUDENT_ID)));
                booking.setCheckInDate(cursor.getString(cursor.getColumnIndex(Booking.KEY_CHECK_IN_DATE)));
                booking.setCheckOutDate(cursor.getString(cursor.getColumnIndex(Booking.KEY_CHECK_OUT_DATE)));
                booking.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(Booking.KEY_TOTAL_PRICE)));
                booking.setRoomType(cursor.getString(cursor.getColumnIndex(Booking.KEY_ROOM_TYPE)));

                bookings.add(booking);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return bookings list
        return bookings;
    }


    public Student getStudent(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Student.TABLE_NAME,
            new String[]{Student.KEY_ID ,Student.KEY_NAME,Student.KEY_EMAIL,Student.KEY_REGISTRATION,Student.KEY_GUARDIAN,Student.KEY_PHONE},
                Student.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        @SuppressLint("Range") Student student = new Student(
                cursor.getInt(cursor.getColumnIndex(Student.KEY_ID)),
                cursor.getString(cursor.getColumnIndex(Student.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(Student.KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndex(Student.KEY_REGISTRATION)),
                cursor.getString(cursor.getColumnIndex(Student.KEY_GUARDIAN)),
                cursor.getString(cursor.getColumnIndex(Student.KEY_PHONE)));

        // close the db connection
        cursor.close();

        return student;
    }

    public Hostel getHostel(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{Hostel.KEY_ID,Hostel.KEY_STUDENT_ID ,Hostel.KEY_NAME, Hostel.KEY_DESCRIPTION,Hostel.KEY_ADDRESS,Hostel.KEY_CITY,Hostel.KEY_COUNTRY,Hostel.KEY_CAPACITY},
                Hostel.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        @SuppressLint("Range") Hostel hostel = new Hostel(
                cursor.getInt(cursor.getColumnIndex(Hostel.KEY_ID)),
                cursor.getInt(cursor.getColumnIndex(Hostel.KEY_STUDENT_ID)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_CITY)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_COUNTRY)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_FULL_NAME)),
                cursor.getInt(cursor.getColumnIndex(Hostel.KEY_IS_ADMIN)) == 1,
                cursor.getInt(cursor.getColumnIndex(Hostel.KEY_CAPACITY)));

        // close the db connection
        cursor.close();

        return hostel;
    }

    // Get Rooms
    public Room getRoom(int id) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define selection arguments
        String[] selectionArgs = {String.valueOf(id)};

        // Query the RoomAllocation table
        Cursor cursor = db.query(
                Room.TABLE_NAME, // Table name
                null, // Select all columns (you can specify specific columns if needed)
                Room.KEY_ID + "=?", // Selection criteria
                selectionArgs, // Selection arguments
                null, // Group by
                null, // Having
                null, // Order by
                null // Limit
        );

        if (cursor != null) {
            cursor.moveToFirst();

            // Create a Room object from cursor data
            @SuppressLint("Range") Room room = new Room(
                    cursor.getInt(cursor.getColumnIndex(Room.KEY_ID)),
                    cursor.getInt(cursor.getColumnIndex(Room.KEY_HOSTEL_ID)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_ROOM_TYPE)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_CAPACITY)),
                    cursor.getInt(cursor.getColumnIndex(Room.KEY_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_STATUS))

            );

            cursor.close();
            return room;
        } else {
            // No room found with the provided id
            return null;
        }
    }

    // Update Rooms

    public int updateRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Update room details (replace with specific columns to update)
        values.put(Room.KEY_ROOM_TYPE, room.getRoom_type());
        values.put(Room.KEY_CAPACITY, room.getCapacity());
        values.put(Room.KEY_PRICE, room.getPrice());
        values.put(Room.KEY_DESCRIPTION, room.getDescription());

        // Update row in RoomAllocation table based on room_id
        return db.update(Room.TABLE_NAME, values, Room.KEY_ID + " = ?",
                new String[]{String.valueOf(room.getId())});
    }

// Delete A room
public void deleteRoom(Room room) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(Room.TABLE_NAME, Room.KEY_ID + " = ?",
            new String[]{String.valueOf(room.getId())});
    db.close();
}



    @SuppressLint("Range")
    public List<Hostel> getAllHostels() {
        List<Hostel> hostels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                Hostel.KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hostel hostel = new Hostel();
                hostel.setId(cursor.getInt(cursor.getColumnIndex(Hostel.KEY_ID)));
                hostel.setId(cursor.getInt(cursor.getColumnIndex(Hostel.KEY_STUDENT_ID)));
                hostel.setHostelName(cursor.getString(cursor.getColumnIndex(Hostel.KEY_NAME)));
                hostel.setDescription(cursor.getString(cursor.getColumnIndex(Hostel.KEY_DESCRIPTION)));
                hostel.setAddress(cursor.getString(cursor.getColumnIndex(Hostel.KEY_ADDRESS)));
                hostel.setCity(cursor.getString(cursor.getColumnIndex(Hostel.KEY_CITY)));
                hostel.setCountry(cursor.getString(cursor.getColumnIndex(Hostel.KEY_COUNTRY)));
                hostel.setCapacity(cursor.getInt(cursor.getColumnIndex(Hostel.KEY_CAPACITY)));

                hostels.add(hostel);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return hostels;
    }

    @SuppressLint("Range")
    public Hostel getHostelById(int hostelId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Hostel.TABLE_NAME + " WHERE " +
                Hostel.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(hostelId)});

        Hostel hostel = null;
        if (cursor.moveToFirst()) {
            hostel = new Hostel();
            hostel.setId(cursor.getInt(cursor.getColumnIndex(Hostel.KEY_ID)));
        }

        cursor.close();
        db.close();

        return hostel;
    }
// List ALl Rooms

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        // Select all columns from RoomAllocation table
        String selectQuery = "SELECT * FROM " + Room.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Loop through rows and create Room objects
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Room room = new Room(
                        cursor.getInt(cursor.getColumnIndex(Room.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Room.KEY_HOSTEL_ID)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_HOSTEL_NAME)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_ROOM_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_CAPACITY)),
                        cursor.getInt(cursor.getColumnIndex(Room.KEY_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_STATUS)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_BOOKING_DATE))
                );
                rooms.add(room);
            } while (cursor.moveToNext());
        }
        // Close cursor and database connection
        cursor.close();
        db.close();

        return rooms;
    }

// Get All Rooms Count

    public int getRoomsCount() {
        String countQuery = "SELECT * FROM " + Room.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // Get the count directly from the cursor
        int count = cursor.getCount();

        cursor.close();

        // Return the count
        return count;
    }

    //Get all rooms associated with a hostel

    // Get the count of rooms associated with a specific hostel
    public int getHostelRoomsCount(int hostelId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Construct the query to count rooms for a specific hostel
        String countQuery = "SELECT * FROM " + Room.TABLE_NAME + " WHERE " + Room.KEY_HOSTEL_ID + " = " + hostelId;

        // Execute the query
        Cursor cursor = db.rawQuery(countQuery, null);

        // Get the count directly from the cursor
        int count = cursor.getCount();

        cursor.close();

        // Return the count
        return count;
    }


}
