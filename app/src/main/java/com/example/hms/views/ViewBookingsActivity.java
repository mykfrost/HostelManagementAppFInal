package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.R;

public class ViewBookingsActivity extends AppCompatActivity {
    private TextView textViewBookingId, textViewStudentName, textViewCheckInDate,
            textViewCheckOutDate, textViewRoomType, textViewTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize TextViews
        textViewBookingId = findViewById(R.id.textViewBookingId);
        textViewStudentName = findViewById(R.id.textViewStudentName);
        textViewCheckInDate = findViewById(R.id.textViewCheckInDate);
        textViewCheckOutDate = findViewById(R.id.textViewCheckOutDate);
        textViewRoomType = findViewById(R.id.textViewRoomType);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            int bookingId = intent.getIntExtra("booking_id", 0);
            String studentName = intent.getStringExtra("student_name");
            String checkInDate = intent.getStringExtra("check_in_date");
            String checkOutDate = intent.getStringExtra("check_out_date");
            String roomType = intent.getStringExtra("room_type");
            double totalPrice = intent.getDoubleExtra("total_price", 0.0);

            // Set text to TextViews
            textViewBookingId.setText("Booking ID: " + bookingId);
            textViewStudentName.setText("Student Name: " + studentName);
            textViewCheckInDate.setText("Check-in Date: " + checkInDate);
            textViewCheckOutDate.setText("Check-out Date: " + checkOutDate);
            textViewRoomType.setText("Room Type: " + roomType);
            textViewTotalPrice.setText("Total Price: $" + totalPrice);
        } else {
            Toast.makeText(this, "Intent extras not found", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if intent extras are missing
        }
    }
}