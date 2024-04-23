package com.example.hms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookRoomActivity extends AppCompatActivity {
    TextView hostelNameTextView;
    DatabaseHandler databaseHandler;
    SessionManager sessionManager;
    private String[] roomTypes;
    EditText  editTextPrice , editTextCheckInDate, editTextCheckOutDate;
    private Spinner spinnerRoomType;
    private SimpleDateFormat dateFormatter;

    public int hostel_id ;
    public int roomid;
    Button buttonBookRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         hostelNameTextView = findViewById(R.id.textViewSelectedHostel);
        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);
        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
        buttonBookRoom = findViewById(R.id.buttonBookRoom);

        roomTypes = getResources().getStringArray(R.array.room_types);
        ArrayAdapter<String> roomsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomsAdapter);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        // Set current date to check-in date if not selected
        editTextCheckInDate.setText(dateFormatter.format(Calendar.getInstance().getTime()));
        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String hostelName = intent.getStringExtra("hostel_name");
           hostel_id = intent.getIntExtra("hostel_id",-1);

//           String roomtype = intent.getStringExtra("room_type");
//           String status = intent.getStringExtra("status");
//           String capacity = intent.getStringExtra("capacity");
//           String price = intent.getStringExtra("total_price");
            // Set text to TextViews
            hostelNameTextView.setText(hostelName);

        }
        // Set a listener for room type selection to update the price field
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomType = roomTypes[position];
                double price = calculatePrice(selectedRoomType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        // Set listener for check-in date EditText to open date picker
        editTextCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextCheckInDate);
            }
        });

        // Set listener for check-out date EditText to open date picker
        editTextCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextCheckOutDate);
            }
        });

        // Initialize the database handler
        databaseHandler = new DatabaseHandler(this,sessionManager);

        // Set listener for book room button
        buttonBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookRoom();
            }
        });
    }

    private double calculatePrice(String roomType) {
        // Add your logic to calculate price based on room type
        // For demonstration, let's assume fixed prices for each room type
        switch (roomType) {
            case "Dorm Room":
                return 250.0;
            case "Private Room":
                return 300.0;
            case "Ensuite Room":
                return 450.0;
            case "Female Dorm":
                return 360.0;
            case "Pod Dorm":
                return 170.0;
            case "Family Room":
                return 200.0;
            default:
                return 0.0;
        }
    }



    private void showDatePickerDialog(final EditText targetEditText) {
        final Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                String selectedDateString = dateFormatter.format(selectedDate.getTime());
                targetEditText.setText(selectedDateString);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void bookRoom() {
        // Get the selected hostel name from the TextView
        String hostelName = hostelNameTextView.getText().toString();
       Room room = new Room();
        roomid = room.getId();
        // Get the selected room type from the Spinner
        String roomType = spinnerRoomType.getSelectedItem().toString();

        // Get the check-in date (booking date)
        String checkInDate = editTextCheckInDate.getText().toString();

        // Get the check-out date from the EditText
        String checkOutDate = editTextCheckOutDate.getText().toString();

        // Get the total price from the EditText
       // double totalPrice = Double.parseDouble(editTextPrice.getText().toString());
        double totalPrice = Double.parseDouble(editTextPrice.getText().toString());

        // Fetch the student's name from the database
        int studentID = fetchStudenID(); // Implement this method
        String studentName = fetchStudenName();

        // Now insert the data into the bookings table
        long bookingId = databaseHandler.addBooking(roomid, hostel_id,hostelName,studentID ,studentName, checkInDate, checkOutDate,checkOutDate, totalPrice);

        if (bookingId != -1) {
            // Booking added successfully
            clearFields();
            Toast.makeText(this, "Booking successful with ID: " + bookingId, Toast.LENGTH_SHORT).show();
        } else {
            // Error adding booking
            Toast.makeText(this, "Failed to book room", Toast.LENGTH_SHORT).show();
        }
    }
    private void clearFields() {
        // Clear all EditText fields
        editTextCheckInDate.setText("");
        editTextCheckOutDate.setText("");
        editTextPrice.setText("");
    }


    private int fetchStudenID() {
        // Implement the logic to fetch the student's name from the database
        // For demonstration, let's assume the name is retrieved from a SessionManager or similar class
        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            return sessionManager.getUserID();
        } else {
            return 0; // Default name if not logged in
        }
    }

    private String fetchStudenName() {
        // Implement the logic to fetch the student's name from the database
        // For demonstration, let's assume the name is retrieved from a SessionManager or similar class
        SessionManager sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            return sessionManager.getFullName();
        } else {
            return "Unknown User"; // Default name if not logged in
        }
    }
}