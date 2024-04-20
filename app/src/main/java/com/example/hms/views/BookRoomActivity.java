package com.example.hms.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.hms.utils.Hostel;
import com.example.hms.utils.Room;
import com.example.hms.utils.Student;

public class BookRoomActivity extends AppCompatActivity {
    private EditText
            editTextCheckInDate, editTextCheckOutDate, editTextTotalPrice;
    TextView  editTextRoomId, editTextHostelId, editTextStudentId;
    private Button buttonBook;

    private  Room room ;
    private Hostel hostel ;
    private Student student ;
     SessionManager sessionManager;
     DatabaseHandler dbhandler;

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
        sessionManager = new SessionManager(this);
        dbhandler = new DatabaseHandler(this, sessionManager);
        // Get Hostel STudent & Rooms

        // Fetch Hostel and Student details from the database based on IDs using DatabaseHandler
        hostel = dbhandler.getHostelById(hostel.getId());
        student = dbhandler.getStudentById(student.getId());

        // Set TextViews with fetched data
        if (hostel != null) {
            editTextHostelId.setText(hostel.getHostelName());
        }
        if (student != null) {
            editTextStudentId.setText(student.getStudent_name());
        }


        editTextRoomId = findViewById(R.id.editTextRoomId);
        editTextHostelId = findViewById(R.id.editTextHostelId);
        editTextStudentId = findViewById(R.id.editTextStudentId);
        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);

        editTextTotalPrice = findViewById(R.id.editTextTotalPrice);
        buttonBook = findViewById(R.id.buttonBook);
        // Check user role and set visibility accordingly
        if (sessionManager.isAdmin()) {
            editTextRoomId.setVisibility(View.VISIBLE);
            editTextHostelId.setVisibility(View.VISIBLE);
            editTextStudentId.setVisibility(View.VISIBLE);
            buttonBook.setVisibility(View.VISIBLE);
            //Get Student Hostel and Room

        } else {
            editTextRoomId.setVisibility(View.GONE);
            editTextHostelId.setVisibility(View.GONE);
            editTextStudentId.setVisibility(View.GONE);
            buttonBook.setVisibility(View.VISIBLE);
        }


        buttonBook.setOnClickListener(v -> bookRoom());
    }
    private void bookRoom() {
        // Get values from EditText fields
        int roomId = room.getId();
        int hostelId = hostel.getId();
        int studentId = student.getId();
        //  Get the hostel name
        String checkInDate = editTextCheckInDate.getText().toString().trim();
        String checkOutDate = editTextCheckOutDate.getText().toString().trim();

        double totalPrice = Double.parseDouble(editTextTotalPrice.getText().toString().trim());

        // Set values to the Room object
        room.setId(roomId);
        room.setHostel_id(hostelId);
        room.setStudent_id(studentId);

        // Perform validation or data processing here

        // For demonstration purposes, display a toast with the booking details
        String message = "Room ID: " + room.getId() + "\nHostel ID: " + room.getHostel_id() +
                "\nStudent ID: " + room.getStudent_id() + "\nCheck-in Date: " + checkInDate +
                "\nTotal Price: " + totalPrice;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}