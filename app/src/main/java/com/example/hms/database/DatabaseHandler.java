package com.example.hms.database;
import static com.example.hms.utils.Hostel.TABLE_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hms.SessionManager;
import com.example.hms.Singleton.HostelCallback;
import com.example.hms.Singleton.VolleySingleton;
import com.example.hms.utils.Booking;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.Room;
import com.example.hms.utils.Student;
import com.example.hms.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final SessionManager sessionManager;
    public VolleySingleton singleton ;
    private RequestQueue requestQueue;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hostel_management";



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

    public void uploadDatabaseToServer() {
        String url = "https://pmenergies.co.ke/android/upload_database.php"; // Replace with your server endpoint URL

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Query to fetch all data from all tables
        String selectQuery = "SELECT * FROM " + User.TABLE_NAME +
                " UNION ALL SELECT * FROM " + Hostel.TABLE_NAME +
                " UNION ALL SELECT * FROM " + Room.TABLE_NAME +
                " UNION ALL SELECT * FROM " + Student.TABLE_NAME +
                " UNION ALL SELECT * FROM " + Booking.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Convert cursor data to JSON
        JSONArray jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {
                int columns = cursor.getColumnCount();
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < columns; i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    try {
                        jsonObject.put(columnName, columnValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray.put(jsonObject);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        // Create a JSON object with the data array
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("data", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a new JsonObjectRequest using POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {
                    // Handle the server response here
                    try {
                        String message = response.getString("message");
                        int status = response.getInt("status");
                        if (status == 0) {
                            // Database uploaded successfully
                            Log.d("UploadDatabase", message);
                        } else {
                            // Error uploading database
                            Log.e("UploadDatabase", message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle Volley errors here
                    Log.e("UploadDatabase", "Volley Error: " + error.toString());
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
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



    public long addRoom(  String room_type, String capacity, String price, String description) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Room.KEY_ROOM_TYPE, room_type);
        values.put(Room.KEY_CAPACITY, capacity);
        values.put(Room.KEY_PRICE, price);
        values.put(Room.KEY_DESCRIPTION, description);

        // insert row
        long id = db.insert(Room.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long addHostel(String hostel_name, String description, String address, String city, String country,String capacity) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Hostel.KEY_NAME, hostel_name);
        values.put(Hostel.KEY_DESCRIPTION, description);
        values.put(Hostel.KEY_ADDRESS, address);
        values.put(Hostel.KEY_CITY, city);
        values.put(Hostel.KEY_COUNTRY, country);
        values.put(Hostel.KEY_CAPACITY, capacity);

        // insert row
        long id = db.insert(Hostel.TABLE_NAME, null, values);

        // close db connection
        db.close();
        // return newly inserted row id
        // Get user role and email from session manager
        boolean isAdmin = sessionManager.isAdmin();
        String email = sessionManager.getEmail();
        String fullName = sessionManager.getFullName();

        // Call the PHP script to upload hostel details to the server
        //uploadHostelToServer(hostel_name, description, address, city, country, email, fullName, isAdmin);
        // return newly inserted row id
        return id;
    }

//    private void uploadHostelToServer(String hostel_name, String description, String address, String city, String country, String email, String full_name, boolean is_admin) {
//        String url = "https://pmenergies.co.ke/android/add_hostel.php";
//
//        // Create a JSONObject to hold the parameters
//        JSONObject params = new JSONObject();
//        try {
//            params.put("hostel_name", hostel_name);
//            params.put("description", description);
//            params.put("address", address);
//            params.put("city", city);
//            params.put("country", country);
//            params.put("email", email);
//            params.put("full_name", full_name);
//            params.put("is_admin", is_admin ? 1 : 0); // Convert boolean to int (1 for true, 0 for false)
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Create a new JsonObjectRequest using POST method
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
//                response -> {
//                    // Handle the server response here
//                    try {
//                        String message = response.getString("message");
//                        int status = response.getInt("status");
//                        if (status == 0) {
//                            // Hostel uploaded successfully
//                            Log.d("UploadHostel", message);
//                        } else {
//                            // Error uploading hostel
//                            Log.e("UploadHostel", message);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                },
//                error -> {
//                    // Handle Volley errors here
//                    Log.e("UploadHostel", "Volley Error: " + error.toString());
//                });
//        // Add the request to the RequestQueue
//
//        // **Fix: Get the VolleySingleton instance and use it to access the request queue**
//        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);
//
//    }


    public long addBooking( String check_in, String check_out, String number_of_students , String price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Booking.KEY_CHECK_IN_DATE, check_in);
        values.put(Booking.KEY_CHECK_OUT_DATE, check_out);
        values.put(Booking.KEY_NUMBER_OF_STUDENTS, number_of_students);
        values.put(Booking.KEY_TOTAL_PRICE, price);

        // insert row
        long id = db.insert(Student.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
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
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_EMAIL)),
                cursor.getString(cursor.getColumnIndex(Hostel.KEY_FULL_NAME)),
                cursor.getInt(cursor.getColumnIndex(Hostel.KEY_IS_ADMIN)),
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
                    cursor.getInt(cursor.getColumnIndex(Room.KEY_STUDENT_ID)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_ROOM_TYPE)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_CAPACITY)),
                    cursor.getInt(cursor.getColumnIndex(Room.KEY_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Room.KEY_DESCRIPTION))
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
                new String[]{String.valueOf(room.getRoom_id())});
    }

// Delete A room
public void deleteRoom(Room room) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(Room.TABLE_NAME, Room.KEY_ID + " = ?",
            new String[]{String.valueOf(room.getRoom_id())});
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

//    @SuppressLint("Range")
//    public List<Hostel> getAllHostels(final HostelCallback callback) {
//        String url = "https://pmenergies.co.ke/android/fetch_hostels.php"; // Replace this with your server endpoint URL
//
//        RequestQueue queue = VolleySingleton.getRequestQueue();
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        List<Hostel> hostels = new ArrayList<>();
//
//                        try {
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject jsonObject = response.getJSONObject(i);
//
//                                Hostel hostel = new Hostel();
//                                hostel.setId(jsonObject.getInt("hostel_id"));
//                                //hostel.setId(jsonObject.getInt("student_id"));
//                                hostel.setHostelName(jsonObject.getString("hostel_name"));
//                                hostel.setDescription(jsonObject.getString("description"));
//                                hostel.setAddress(jsonObject.getString("address"));
//                                hostel.setCity(jsonObject.getString("city"));
//                                hostel.setCountry(jsonObject.getString("country"));
//
//                                hostels.add(hostel);
//                            }
//
//                            callback.onSuccess(hostels); // Pass the hostels list to the callback
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley Error", error.toString());
//                    }
//                });
//
//        queue.add(jsonArrayRequest);
//    return hostels;
//    }


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
                        cursor.getInt(cursor.getColumnIndex(Room.KEY_STUDENT_ID)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_ROOM_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_CAPACITY)),
                        cursor.getInt(cursor.getColumnIndex(Room.KEY_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Room.KEY_DESCRIPTION))
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

    public Room getRoom(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Room.TABLE_NAME,
                new String[]{Room.KEY_ID , Room.KEY_STUDENT_ID,Room.KEY_HOSTEL_ID, Room.KEY_ROOM_TYPE, Room.KEY_CAPACITY,Room.KEY_PRICE, Room.KEY_DESCRIPTION},
                Hostel.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        @SuppressLint("Range") Room rooms = new Room(
                cursor.getInt(cursor.getColumnIndex(Room.KEY_ID)),
                cursor.getInt(cursor.getColumnIndex(Room.KEY_STUDENT_ID)),
                cursor.getInt(cursor.getColumnIndex(Room.KEY_HOSTEL_ID)),
                cursor.getString(cursor.getColumnIndex(Room.KEY_ROOM_TYPE)),
                cursor.getString(cursor.getColumnIndex(Room.KEY_CAPACITY)),
                cursor.getInt(cursor.getColumnIndex(Room.KEY_PRICE)),
                cursor.getString(cursor.getColumnIndex(Room.KEY_DESCRIPTION))
            );

        // close the db connection
        cursor.close();

        return rooms;
    }

}