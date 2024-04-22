package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.hms.utils.User;
import com.example.hms.views.AddHostelActivity;
import com.example.hms.views.BookingDetailsActivity;
import com.example.hms.views.CreateNotification;
import com.example.hms.views.HostelsActivity;
import com.example.hms.views.StudentManager;
import com.example.hms.views.SelectHostelActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView tvFullName, tvRole;
    private ProgressBar progressBar;
    private static final String TAG = "Intent Extras";

    private SessionManager sessionManager;
    Button btnViewBooking, btnAddHostels, btnAddRoom, btnStudentManagement, btnViewHostels, btnRoomAvailability, btnBooking, btnFeedbackManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize progress bar
        progressBar = findViewById(R.id.progressBar);
        btnStudentManagement = findViewById(R.id.btnStudentManagement);
        btnViewHostels = findViewById(R.id.btnViewHostels);
        btnRoomAvailability = findViewById(R.id.btnRoomAvailability);
        btnBooking = findViewById(R.id.btnBooking);
        btnViewBooking = findViewById(R.id.btnViewBooking);
        btnFeedbackManagement = findViewById(R.id.btnNFeedbackManagement);
        btnAddHostels = findViewById(R.id.btnAddHostels);
        btnAddRoom = findViewById(R.id.btnAddrooms);
        tvRole = findViewById(R.id.tvRole);
        tvFullName = findViewById(R.id.tvFullName);
        sessionManager = new SessionManager(this);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if intent has extras
        if (intent != null && intent.getExtras() != null) {
            // Retrieve the extras
            String fullName = intent.getStringExtra("full_names");
            String role = intent.getStringExtra("role");

            // Use the retrieved values as needed
            Log.d(TAG, "Full Name: " + fullName);
            Log.d(TAG, "Role: " + role);


            tvFullName.setText("Full Name: " + fullName);
            tvRole.setText("Role: " + role);
        } else {
            Log.e(TAG, "Intent or extras not found.");
        }
        if (sessionManager.isLoggedIn()) {
            User user = new User();
            String fullName = sessionManager.getFullName();
            String userRole = sessionManager.isAdmin() ? "admin" : "user";
            tvFullName.setText(fullName);
            tvRole.setText(userRole);
            // Schedule the database upload work
            //scheduleDatabaseUploadWork();
            // Show user role
            if (sessionManager.isAdmin()) {
                btnAddHostels.setVisibility(View.VISIBLE);
                btnAddRoom.setVisibility(View.VISIBLE);
                btnViewBooking.setVisibility(View.VISIBLE);
            } else {
                btnAddHostels.setVisibility(View.GONE);
                btnViewBooking.setVisibility(View.GONE);
                btnAddRoom.setVisibility(View.GONE);
            }

        } else {
            // If user is not logged in, redirect to LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Close this activity to prevent going back
        }
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent booking = new Intent(getApplicationContext(), SelectHostelActivity.class);
                startActivity(booking);
            }
        });

        btnFeedbackManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), CreateNotification.class);
                startActivity(next);
            }
        });
        btnViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stdManager = new Intent(getApplicationContext(), BookingDetailsActivity.class);
                startActivity(stdManager);
            }
        });
        btnStudentManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stdManager = new Intent(getApplicationContext(), StudentManager.class);
                startActivity(stdManager);
            }
        });
        btnAddHostels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addhostels = new Intent(getApplicationContext(), AddHostelActivity.class);
                startActivity(addhostels);
            }
        });

        btnViewHostels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewHostels = new Intent(getApplicationContext(), HostelsActivity.class);
                startActivity(viewHostels);
            }
        });

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addroom = new Intent(getApplicationContext(), SelectHostelActivity.class);
                startActivity(addroom);
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void scheduleDatabaseUploadWork() {

        // Show progress bar before starting upload work
        showProgressBar();
        // Create a periodic work request for the DatabaseUploadWorker
        PeriodicWorkRequest uploadWorkRequest =
                new PeriodicWorkRequest.Builder(DatabaseUploadWorker.class, 1, TimeUnit.HOURS)
                        .build();

        // Enqueue the work request with WorkManager
        Operation databaseUploadWork = WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                        "DatabaseUploadWork",
                        ExistingPeriodicWorkPolicy.REPLACE,
                        uploadWorkRequest
                );

        // Hide progress bar after enqueuing work
        hideProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            sessionManager.logoutUser();
            return true;
        }
        // Handle other menu item clicks if needed

        return super.onOptionsItemSelected(item);
    }
}