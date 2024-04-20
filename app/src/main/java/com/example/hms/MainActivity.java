package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hms.views.AddHostelActivity;
import com.example.hms.views.AddRoomActivity;
import com.example.hms.views.BookRoomActivity;
import com.example.hms.views.HostelsActivity;
import com.example.hms.views.StudentManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView tvFullName, tvEmail , role;
    private SessionManager sessionManager;
    Button     btnAddHostels ,btnAddRoom, btnAdminPanel ,btnStudentManagement, btnViewHostels  ,btnRoomAvailability, btnBooking ,btnFeedbackManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStudentManagement = findViewById(R.id.btnStudentManagement);
        btnViewHostels = findViewById(R.id.btnViewHostels);
        btnRoomAvailability = findViewById(R.id.btnRoomAvailability);
        btnBooking = findViewById(R.id.btnBooking);
        btnAdminPanel = findViewById(R.id.btnAdminPanel);
        btnFeedbackManagement = findViewById(R.id.btnNFeedbackManagement);
        btnAddHostels = findViewById(R.id.btnAddHostels);
        btnAddRoom = findViewById(R.id.btnAddrooms);
        role = findViewById(R.id.tvRole);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);

        sessionManager = new SessionManager(this);


//        // Check if the user is logged in
//        if ( sessionManager.isLoggedIn() && sessionManager.isAdmin()) {
//            findViewById(R.id.btnAdminPanel).setVisibility(View.VISIBLE);
//        }else {
//            findViewById(R.id.btnAdminPanel).setVisibility(View.GONE);
//        }
// Check if the user is logged in
        if (sessionManager.isLoggedIn()) {
            String fullName = sessionManager.getFullName();
            String email = sessionManager.getEmail();
            String userRole = sessionManager.isAdmin() ? "admin" : "user";
            tvFullName.setText(fullName);
            tvEmail.setText(email);
            role.setText(userRole);
            // Schedule the database upload work
            scheduleDatabaseUploadWork();
            // Show user role
            if (sessionManager.isAdmin()) {
                btnAdminPanel.setVisibility(View.VISIBLE);
                btnAddHostels.setVisibility(View.VISIBLE);
                btnAddRoom.setVisibility(View.VISIBLE);
            } else {
                btnAdminPanel.setVisibility(View.GONE);
                btnAddHostels.setVisibility(View.GONE);
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
            Intent booking = new Intent(getApplicationContext(), BookRoomActivity.class);
            startActivity(booking);
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
                Intent addroom = new Intent(getApplicationContext(), AddRoomActivity.class);
                startActivity(addroom);
            }
        });
    }

    private void scheduleDatabaseUploadWork() {
        // Create a periodic work request for the DatabaseUploadWorker
        PeriodicWorkRequest uploadWorkRequest =
                new PeriodicWorkRequest.Builder(DatabaseUploadWorker.class, 1, TimeUnit.HOURS)
                        .build();

        // Enqueue the work request with WorkManager
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                        "DatabaseUploadWork",
                        ExistingPeriodicWorkPolicy.REPLACE,
                        uploadWorkRequest
                );
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