package com.example.hms.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomDetailActivity extends AppCompatActivity {
   Button book ;
    private SimpleDateFormat dateFormatter;
   DatabaseHandler dbhandler;
   SessionManager sessionManager ;
   int hostelid ,studentid, roomId;
   EditText checkout;

   TextView  roomHostelName ,roomTypeTextView ,  statusTextView, textviewdescription ,capacityTextView,totalPriceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve the extras from the Intent using the keys
            String hostelName = intent.getStringExtra("hostel_name");
            hostelid = intent.getIntExtra("hostel_id",0);
            roomId = intent.getIntExtra("room_id", 0);
            String roomType = intent.getStringExtra("room_type");
            String status = intent.getStringExtra("status");
            int capacity = intent.getIntExtra("capacity", 0); // Default value if not found
            String description = intent.getStringExtra("description");
            double totalPrice = intent.getDoubleExtra("total_price", 0.0);

            // Now you can use these values to display details in your activity
            roomHostelName = findViewById(R.id.room_hostel_name);
             roomTypeTextView = findViewById(R.id.room_type_text_view);
             statusTextView = findViewById(R.id.status_text_view);
             capacityTextView = findViewById(R.id.capacity_text_view);
             totalPriceTextView = findViewById(R.id.total_price_text_view);
            textviewdescription = findViewById(R.id.description);
            checkout = findViewById(R.id.checkOut_date);
            book = findViewById(R.id.newButtonBook);


            roomHostelName.setText("Room ID: " + hostelName);
            textviewdescription.setText("Desc: "+ description);
            roomTypeTextView.setText("Room Type: " + roomType);
            statusTextView.setText("Status: " + status);
            capacityTextView.setText("Capacity: " + capacity);
            totalPriceTextView.setText("Total Price: Ksh" + totalPrice);
        } else {
            // Handle the case where intent is null
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if intent is null
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Booking Added", Toast.LENGTH_SHORT).show();
                bookRoom();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkout);
            }
        });
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

    private void bookRoom() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        studentid = fetchStudenID();
        // Format the date using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String checkin = dateFormat.format(currentDate);
        String checkOutDate = checkout.getText().toString();
        String hostelname = roomHostelName.getText().toString().trim();
        String roomtype = roomTypeTextView.getText().toString().trim();
        String status = statusTextView.getText().toString().trim();
        String studentname = fetchStudenName();
        String desc = textviewdescription.getText().toString().trim();
        String capacity = capacityTextView.getText().toString().trim();
        String price = totalPriceTextView.toString().trim();

        double parsedPrice = Double.parseDouble(price);

      //  List<Room> rooms = dbhandler.getAllRooms();
        long id = dbhandler.addBooking(roomId,hostelid,hostelname,studentid ,studentname ,roomtype , checkin , checkOutDate, parsedPrice);
    }
}