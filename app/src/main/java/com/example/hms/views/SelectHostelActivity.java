package com.example.hms.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.adapters.HostelAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.Room;
import com.example.hms.utils.Student;

import java.util.ArrayList;
import java.util.List;

public class SelectHostelActivity extends AppCompatActivity {
    private EditText
            editTextCheckInDate, editTextCheckOutDate, editTextTotalPrice;
    TextView  editTextRoomId, editTextHostelId, editTextStudentId;
    private Button buttonBook;
    SessionManager sessionManager ;
    private HostelAdapter adapter;
    private List<Hostel> hostelList;
    private  Room room ;
    private Hostel hostel ;
    private Student student ;
     DatabaseHandler databaseHelper;
     RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_hostel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHandler(this, sessionManager);
        // Get Hostel STudent & Rooms

//        // Fetch Hostel and Student details from the database based on IDs using DatabaseHandler
//        int userId = sessionManager.getUserID(); // Assuming this method retrieves user ID
//        student = dbhandler.getStudentById(userId);
//
//        // Set TextViews with fetched data
//        // Set TextViews with fetched data
//        if (student != null) {
//            editTextStudentId.setText(String.valueOf(student.getId()));
//            editTextHostelId.setText(String.valueOf(student.getId()));
//        }
//        editTextRoomId = findViewById(R.id.editTextRoomId);
//        editTextHostelId = findViewById(R.id.editTextHostelId);
//        editTextStudentId = findViewById(R.id.editTextStudentId);
//        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
//        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);
//
//        editTextTotalPrice = findViewById(R.id.editTextTotalPrice);
//        buttonBook = findViewById(R.id.buttonBook);

        recyclerView = findViewById(R.id.selectHostelRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hostelList = new ArrayList<>();


        // Initialize database helper
        sessionManager = new SessionManager(getApplicationContext());
        databaseHelper = new DatabaseHandler(getApplicationContext(), sessionManager);

        // Fetch data from the database and populate the hostelList
        hostelList.addAll(databaseHelper.getAllHostels());

        // Initialize RecyclerView adapter
        adapter = new HostelAdapter(hostelList, this);

        // Set RecyclerView layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Check user role and set visibility accordingly
//        if (sessionManager.isAdmin()) {
//            editTextRoomId.setVisibility(View.VISIBLE);
//            editTextHostelId.setVisibility(View.VISIBLE);
//            editTextStudentId.setVisibility(View.VISIBLE);
//            buttonBook.setVisibility(View.VISIBLE);
//            //Get Student Hostel and Room
//
//        } else {
//            editTextRoomId.setVisibility(View.GONE);
//            editTextHostelId.setVisibility(View.GONE);
//            editTextStudentId.setVisibility(View.GONE);
//            buttonBook.setVisibility(View.VISIBLE);
//        }


        //buttonBook.setOnClickListener(v -> bookRoom());
    }
//    private void bookRoom() {
//        // Get values from EditText fields
//        int roomId = Integer.parseInt(editTextRoomId.getText().toString().trim());
//        int hostelId = Integer.parseInt(editTextHostelId.getText().toString().trim());
//        int studentId = Integer.parseInt(editTextStudentId.getText().toString().trim());
//        String checkInDate = editTextCheckInDate.getText().toString().trim();
//        String checkOutDate = editTextCheckOutDate.getText().toString().trim();
//        double totalPrice = Double.parseDouble(editTextTotalPrice.getText().toString().trim());
//
//        // Perform any additional validation or data processing here
//
//        // For demonstration purposes, display a toast with the booking details
//        String message = "Room ID: " + roomId + "\nHostel ID: " + hostelId +
//                "\nStudent ID: " + studentId + "\nCheck-in Date: " + checkInDate +
//                "\nCheck-out Date: " + checkOutDate + "\nTotal Price: " + totalPrice;
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }
}