package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.R;

public class HostelDetailsActivity extends AppCompatActivity {
    private TextView hostelNameTextView, descriptionTextView, addressTextView, cityTextView, countryTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hostel_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize TextViews
        hostelNameTextView = findViewById(R.id.item_hostel_name);
        descriptionTextView = findViewById(R.id.item_description);
        addressTextView = findViewById(R.id.item_address);
        cityTextView = findViewById(R.id.item_city);
        countryTextView = findViewById(R.id.item_country);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            int hostel_id = intent.getIntExtra("hostel_id",-1);
            String hostelName = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            String address = intent.getStringExtra("address");
            String city = intent.getStringExtra("city");
            String country = intent.getStringExtra("country");

            // Set text to TextViews
            hostelNameTextView.setText(hostelName);
            descriptionTextView.setText(description);
            addressTextView.setText(address);
            cityTextView.setText(city);
            countryTextView.setText(country);
        }
    }
}