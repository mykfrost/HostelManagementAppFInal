package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.LoginActivity;
import com.example.hms.MainActivity;
import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinnerRoomType;
    private EditText editTextCapacity, editTextPrice, editTextDescription;
    private Button buttonAddRoom;
    private DatabaseHandler databaseHandler;
    private SessionManager sessionManager;
    private String[] roomTypes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);
        roomTypes = new String[]{"Dorm Room", "Private Room", "Ensuite Room", "Female Dorm", "Pod Dorm", "Family Room"};
        // Initialize DatabaseHandler and SessionManager
        databaseHandler = new DatabaseHandler(this, sessionManager);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        // Set click listener for the "Add Room" button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(adapter);

//        // Check if the user is logged in
//        if (sessionManager.isLoggedIn()) {
//            String fullName = sessionManager.getFullName();
//            String email = sessionManager.getEmail();
//            String userRole = sessionManager.isAdmin() ? "admin" : "user";
//
//
//        } else {
//            // If user is not logged in, redirect to LoginActivity
//            Intent loginIntent = new Intent(this, LoginActivity.class);
//            startActivity(loginIntent);
//            finish(); // Close this activity to prevent going back
//        }
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomToDatabase();
            }
        });
    }
    private void addRoomToDatabase() {
        String roomType = spinnerRoomType.getSelectedItem().toString();
        String capacity = editTextCapacity.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (roomType.isEmpty() || capacity.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        // Add room to database
        long roomId = databaseHandler.addRoom(roomType, capacity, price, description);

        if (roomId != -1) {
            // Room added successfully
            Toast.makeText(this, "Room added successfully with ID: " + roomId, Toast.LENGTH_SHORT).show();
            // Clear input fields
            clearInputFields();
        } else {
            // Error adding room
            Toast.makeText(this, "Failed to add room", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        editTextCapacity.getText().clear();
        editTextPrice.getText().clear();
        editTextDescription.getText().clear();
    }
}