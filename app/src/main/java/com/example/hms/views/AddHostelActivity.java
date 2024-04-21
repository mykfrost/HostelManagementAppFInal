package com.example.hms.views;

import static com.example.hms.Singleton.VolleySingleton.context;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.Singleton.VolleySingleton;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;

import org.json.JSONException;
import org.json.JSONObject;

public class AddHostelActivity extends AppCompatActivity {
    private EditText editTextHostelName,editTextCapacity, editTextDescription, editTextAddress, editTextCity, editTextCountry;
    private DatabaseHandler databaseHelper;
    Button btnAddHostel;
SessionManager   sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel);
        sessionManager = new SessionManager(this);
// Get user information from SessionManager


        editTextHostelName = findViewById(R.id.edtHostelName);
        editTextDescription = findViewById(R.id.edTextDescription);
        editTextAddress = findViewById(R.id.edTextAddress);
        editTextCity = findViewById(R.id.edTextCity);
        editTextCountry = findViewById(R.id.edTextCountry);
        editTextCapacity = findViewById(R.id.editTextCapacity);
         btnAddHostel = findViewById(R.id.btnAddHostel);

         databaseHelper = new DatabaseHandler(this, sessionManager);

         btnAddHostel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                AddHostel();
                Toast.makeText(getApplicationContext(),"Hostel Added",Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void AddHostel() {
        String hostelName = editTextHostelName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();
       // int capacity = Integer.parseInt(editTextCapacity.getText().toString().trim());

        // Validate capacity input
        String capacityInput = editTextCapacity.getText().toString().trim();
        if (capacityInput.isEmpty()) {
            // Show error message for empty capacity
            editTextCapacity.setError("Capacity is required");
            editTextCapacity.requestFocus();
            return;
        }

        int capacity;
        try {
            // Try parsing capacity input as an integer
            capacity = Integer.parseInt(capacityInput);
        } catch (NumberFormatException e) {
            // Show error message for invalid capacity format
            editTextCapacity.setError("Enter a valid capacity");
            editTextCapacity.requestFocus();
            return;
        }


        // Parse capacity as int
        String fullName = sessionManager.getFullName();
//        String email = sessionManager.getEmail();
        boolean isAdmin = sessionManager.isAdmin();

        // Create Hostel object
       // Hostel hostel = new Hostel(hostelName , description, address, city, country, fullName,isAdmin,capacity);

       long id = databaseHelper.addHostel(hostelName, description, address, city, country, fullName,isAdmin , capacity);
        if (id != -1) {
            Toast.makeText(getApplicationContext(), "Hostel Added", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after adding the hostel
        } else {
            Toast.makeText(getApplicationContext(), "Failed to add hostel", Toast.LENGTH_SHORT).show();
        }

    }
}