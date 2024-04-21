package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.hms.adapters.HostelAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinnerRoomType,spinnerHostels;
    private EditText editTextCapacity, editTextPrice, editTextDescription;
    private Button buttonAddRoom;
     private ArrayAdapter<String> hostelAdapter;
    private DatabaseHandler databaseHandler;
    private SessionManager sessionManager;
    private List<Hostel> hostelsList;
    private double[] roomPrices;
    private String[] roomTypes ,  hostelNames;
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
        spinnerHostels = findViewById(R.id.spinnerHostels);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);

        roomTypes = getResources().getStringArray(R.array.room_types);
        ArrayAdapter<String> roomsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomsAdapter);

        databaseHandler = new DatabaseHandler(this, sessionManager);
        sessionManager = new SessionManager(this);

        // Fetch hostels from the database
        hostelsList = databaseHandler.getAllHostels();
        if (hostelsList != null && !hostelsList.isEmpty()) {
            List<String> hostelNames = new ArrayList<>();
            for (Hostel hostel : hostelsList) {
                hostelNames.add(hostel.getHostelName());
            }
            hostelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hostelNames);
            hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHostels.setAdapter(hostelAdapter);
        }

        // Set a listener for room type selection to update the price field
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomType = roomTypes[position];
                double price = calculatePrice(selectedRoomType);
                editTextPrice.setText(String.valueOf(price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomToDatabase();
            }
        });
    }
    private void addRoomToDatabase() {
        String selectedHostel = spinnerHostels.getSelectedItem().toString();
        String roomType = spinnerRoomType.getSelectedItem().toString();
        String capacity = editTextCapacity.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (selectedHostel.isEmpty() || roomType.isEmpty() || capacity.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int hostelId = findHostelId(selectedHostel);

        long roomId = databaseHandler.addRoom(roomType, capacity, price, description, hostelId);

        if (roomId != -1) {
            Toast.makeText(this, "Room added successfully with ID: " + roomId, Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            Toast.makeText(this, "Failed to add room", Toast.LENGTH_SHORT).show();
        }
    }

    private int findHostelId(String hostelName) {
        for (Hostel hostel : hostelsList) {
            if (hostel.getHostelName().equals(hostelName)) {
                return hostel.getId();
            }
        }
        return -1;
    }

    private void clearInputFields() {
        editTextCapacity.getText().clear();
        editTextPrice.getText().clear();
        editTextDescription.getText().clear();
    }

    private double calculatePrice(String roomType) {
        // Add your logic to calculate price based on room type
        // For demonstration, let's assume fixed prices for each room type
        switch (roomType) {
            case "Dorm Room":
                return 50.0;
            case "Private Room":
                return 100.0;
            case "Ensuite Room":
                return 150.0;
            case "Female Dorm":
                return 60.0;
            case "Pod Dorm":
                return 70.0;
            case "Family Room":
                return 200.0;
            default:
                return 0.0;
        }
    }
}