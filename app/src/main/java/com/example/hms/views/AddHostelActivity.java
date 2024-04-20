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
                 String hostelName = editTextHostelName.getText().toString().trim();
                 String description = editTextDescription.getText().toString().trim();
                 String address = editTextAddress.getText().toString().trim();
                 String city = editTextCity.getText().toString().trim();
                 String country = editTextCountry.getText().toString().trim();
                 String capacity = editTextCapacity.getText().toString().trim();

                 boolean isAdmin = sessionManager.isAdmin();
                 String email = sessionManager.getEmail();
                 String fullName = sessionManager.getFullName();

                 long id = databaseHelper.addHostel(hostelName, description, address, city, country, capacity);
                 if (id != -1) {
                     uploadHostelToServer(hostelName,description,address,city,country,email,fullName,isAdmin);
                     Toast.makeText(AddHostelActivity.this, "Hostel added successfully!", Toast.LENGTH_SHORT).show();
                     finish(); // Finish the activity after adding hostel
                 } else {
                     Toast.makeText(AddHostelActivity.this, "Failed to add hostel", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    private void uploadHostelToServer(String hostel_name, String description, String address, String city, String country, String email, String full_name, boolean is_admin) {
        String url = "https://pmenergies.co.ke/android/add_hostel.php";

        // Create a JSONObject to hold the parameters
        JSONObject params = new JSONObject();
        try {
            params.put("hostel_name", hostel_name);
            params.put("description", description);
            params.put("address", address);
            params.put("city", city);
            params.put("country", country);
            params.put("email", email);
            params.put("full_name", full_name);
            params.put("is_admin", is_admin ? 1 : 0); // Convert boolean to int (1 for true, 0 for false)
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a new JsonObjectRequest using POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    // Handle the server response here
                    try {
                        String message = response.getString("message");
                        int status = response.getInt("status");
                        if (status == 0) {
                            // Hostel uploaded successfully
                            Log.d("UploadHostel", message);
                        } else {
                            // Error uploading hostel
                            Log.e("UploadHostel", message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle Volley errors here
                    Log.e("UploadHostel", "Volley Error: " + error.toString());
                });
        // Add the request to the RequestQueue

        // **Fix: Get the VolleySingleton instance and use it to access the request queue**
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
}