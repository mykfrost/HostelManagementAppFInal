package com.example.hms.views;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.hms.utils.Room;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {
    private Spinner spinnerRoomType, spinnerStatus;
    private EditText editTextCapacity, editTextPrice, editTextDescription;
    private Button buttonAddRoom;
    private TextView tvHostels;
    private DatabaseHandler dbHandler;
    private int hostelId;

    private String[] roomTypes, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        tvHostels = findViewById(R.id.selectedHostels);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);

        roomTypes = getResources().getStringArray(R.array.room_types);
        ArrayAdapter<String> roomsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomsAdapter);

        status = getResources().getStringArray(R.array.status);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        dbHandler = new DatabaseHandler(this,new SessionManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hostelId = extras.getInt("hostel_id", -1);
            String hostelName = extras.getString("hostel_name");

            if (hostelId != -1 && hostelName != null) {
                tvHostels.setText(hostelName);
                Log.d("AddRoomActivity", "Hostel ID: " + hostelId);
                Log.d("AddRoomActivity", "Hostel Name: " + hostelName);
            } else {
                Log.e("AddRoomActivity", "Hostel ID or Name not found in extras");
            }
        } else {
            Log.e("AddRoomActivity", "Intent extras bundle is null");
        }

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

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // You can handle status selection here if needed
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
        String hostels = tvHostels.getText().toString().trim();
        String roomType = spinnerRoomType.getSelectedItem().toString();
        String status = spinnerStatus.getSelectedItem().toString();
        int capacity = Integer.parseInt(editTextCapacity.getText().toString());
        double price = Double.parseDouble(editTextPrice.getText().toString());
        String description = editTextDescription.getText().toString();

        Room room = new Room();
        room.setHostel_name(hostels);
        room.setHostel_id(hostelId);
        room.setRoom_type(roomType);
        room.setStatus(status);
        room.setCapacity(String.valueOf(capacity));
        room.setPrice((int) price);
        room.setDescription(description);

        long roomId = dbHandler.addRoom(room);

        if (roomId != -1) {
            Toast.makeText(this, "Room added successfully", Toast.LENGTH_SHORT).show();
            clearInputFields();
        } else {
            Toast.makeText(this, "Failed to add room", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        editTextCapacity.setText("");
        editTextPrice.setText("");
        editTextDescription.setText("");
    }

    private double calculatePrice(String roomType) {
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