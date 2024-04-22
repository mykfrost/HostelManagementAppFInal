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
import com.example.hms.adapters.SelectHostelAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;

import java.util.ArrayList;
import java.util.List;

public class SelectHostelActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    SessionManager sessionManager ;
    DatabaseHandler databaseHelper;
    private SelectHostelAdapter adapter;
    private List<Hostel> hostelist;
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


        recyclerView = findViewById(R.id.recyclerviewRoomsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hostelist = new ArrayList<>();

        // Initialize database helper
        sessionManager = new SessionManager(getApplicationContext());
        databaseHelper = new DatabaseHandler(getApplicationContext(), sessionManager);


        // Fetch data from the database and populate the hostelList
        hostelist.addAll(databaseHelper.getAllHostels());

        adapter = new SelectHostelAdapter(hostelist , this);

        // Set RecyclerView layout manager and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Sample data for hostels
        String[] hostelData = {
                "Cozy Haven Hostel,Comfortable accommodation with friendly atmosphere and great amenities.,123 Main Street,New York,USA,150",
                "Serene Oasis Hostel,Peaceful and tranquil hostel offering a relaxing stay with beautiful surroundings.,456 Park Avenue,Los Angeles,USA,120",
                "Urban Adventure Hostel,Exciting urban experience with modern facilities and vibrant atmosphere.,789 Downtown Boulevard,Chicago,USA,180",
                "Coastal Escape Hostel,Coastal retreat offering stunning ocean views and outdoor activities.,101 Oceanfront Drive,Miami,USA,160",
                "Mountain View Hostel,Spectacular mountain vistas and cozy rooms for a memorable stay.,222 Alpine Road,Denver,USA,190",
                "Cultural Hub Hostel,Immerse yourself in local culture and artsy ambiance at this unique hostel.,333 Artisan Lane,San Francisco,USA,170",
                "Green Oasis Hostel,Eco-friendly accommodation surrounded by lush greenery and nature trails.,444 Eco Avenue,Seattle,USA,130",
                "Lakeside Retreat Hostel,Tranquil lakeside setting with water activities and serene environment.,555 Lakefront Road,Minneapolis,USA,110",
                "Historic Charm Hostel,Stay in a charming historic building with modern amenities and nostalgic vibes.,666 Heritage Lane,Boston,USA,100",
                "Tropical Paradise Hostel,Tropical paradise offering beachfront accommodation and island adventures.,777 Seaside Avenue,Honolulu,USA,200"
        };

        // Process and add hostel data to the list
        for (String hostelStr : hostelData) {
            String[] hostelDetails = hostelStr.split(",");
            Hostel hostel = new Hostel();
            hostel.setHostelName(hostelDetails[0]);
            hostel.setDescription(hostelDetails[1]);
            hostel.setAddress(hostelDetails[2]);
            hostel.setCity(hostelDetails[3]);
            hostel.setCountry(hostelDetails[4]);
            hostel.setCapacity(Integer.parseInt(hostelDetails[5]));
            hostelist.add(hostel);
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }

}