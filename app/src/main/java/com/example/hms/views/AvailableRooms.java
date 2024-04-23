package com.example.hms.views;

import android.os.Bundle;

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
import com.example.hms.adapters.RoomsAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.Room;

import java.util.ArrayList;
import java.util.List;

public class AvailableRooms extends AppCompatActivity {
// listview activity here
RecyclerView recyclerView ;
    SessionManager sessionManager ;

    private RoomsAdapter adapter;
    private List<Room> roomslist;
    DatabaseHandler databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_available_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerAvailableRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomslist = new ArrayList<>();
        // Fetch data from the database and populate the hostelList
        roomslist.addAll(databaseHelper.getAllRooms());

        // Initialize RecyclerView adapter
        adapter = new RoomsAdapter(roomslist, this);

        // Set RecyclerView layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}