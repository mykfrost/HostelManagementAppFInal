package com.example.hms.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.HostelAdapter;

import java.util.ArrayList;
import java.util.List;

public class HostelsActivity extends AppCompatActivity  {
  RecyclerView recyclerView ;
  SessionManager sessionManager ;
    private HostelAdapter adapter;
    private List<Hostel> hostelList;
    DatabaseHandler databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);
        recyclerView = findViewById(R.id.recyclerViewHostels);
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
    }


}