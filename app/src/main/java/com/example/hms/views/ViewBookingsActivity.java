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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.adapters.BookingsAdapter;
import com.example.hms.adapters.SelectHostelAdapter;
import com.example.hms.adapters.ViewBookingsAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Booking;
import com.example.hms.utils.Hostel;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {

    SessionManager sessionManager ;
    private RecyclerView recyclerView;

    DatabaseHandler databaseHelper;
    private BookingsAdapter adapter;
    private List<Booking> bookingList;
    DatabaseHandler dbhandler;
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


        recyclerView = findViewById(R.id.recyclerBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();

        // Initialize database helper
        sessionManager = new SessionManager(getApplicationContext());
        databaseHelper = new DatabaseHandler(getApplicationContext(), sessionManager);


        // Fetch data from the database and populate the hostelList
        bookingList.addAll(databaseHelper.getAllBookings());

        adapter = new BookingsAdapter(  bookingList ,this);

        // Set RecyclerView layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}