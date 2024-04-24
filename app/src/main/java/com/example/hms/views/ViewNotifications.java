package com.example.hms.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.adapters.NotificationAdapter;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Notification;

import java.util.ArrayList;
import java.util.List;

public class ViewNotifications extends AppCompatActivity {
    private RecyclerView recyclerViewNotifications;
    private DatabaseHandler dbHandler;
    private NotificationAdapter notificationsAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        notificationList = new ArrayList<>();
        notificationsAdapter = new NotificationAdapter(this, notificationList);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotifications.setAdapter(notificationsAdapter);

        // Initialize DatabaseHandler
        dbHandler = new DatabaseHandler(this , new SessionManager(this));

        // Populate notificationList with data from the database (retrieve notifications)
        getAllNotifications();
    }

    private void getAllNotifications() {
        // Retrieve notifications from the database
        List<Notification> notifications = dbHandler.getAllNotifications();

        if (notifications != null && !notifications.isEmpty()) {
            // Add retrieved notifications to notificationList
            notificationList.addAll(notifications);
            // Notify adapter about data changes
            notificationsAdapter.notifyDataSetChanged();
        } else {
            // No notifications found, show a toast message
            Toast.makeText(this, "No notifications found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection
        if (dbHandler != null) {
            dbHandler.close();
        }
    }
}