package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.utils.Hostel;
import com.example.hms.utils.User;

public class CreateNotification extends AppCompatActivity {
    private EditText editTextHostelId, editTextSenderId, editTextReceiverId, editTextMessage;
    private Button buttonSendNotification , buttonViewNotifications;
    SessionManager sessionManager;
    DatabaseHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize database handler
        dbHandler = new DatabaseHandler(this , sessionManager);

        editTextHostelId = findViewById(R.id.editTextHostelId);
        editTextSenderId = findViewById(R.id.editTextSenderId);
        editTextReceiverId = findViewById(R.id.editTextReceiverId);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonViewNotifications = findViewById(R.id.buttonViewNotifications);
        buttonSendNotification = findViewById(R.id.buttonSendNotification);
        buttonSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        buttonViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ViewNotifications.class);
                startActivity(next);
            }
        });
    }
    private void sendNotification() {
        // Get input values
        String hostelIdText = editTextHostelId.getText().toString();
        String senderIdText = editTextSenderId.getText().toString();
        String receiverIdText = editTextReceiverId.getText().toString();
        dbHandler  = new DatabaseHandler(this, sessionManager);
        Hostel hostel = new Hostel();
        User user  = new User();

        String message = editTextMessage.getText().toString();

        // Check if input values are not empty
        if (!TextUtils.isEmpty(hostelIdText) && !TextUtils.isEmpty(senderIdText)
                && !TextUtils.isEmpty(receiverIdText) && !TextUtils.isEmpty(message)) {
            int hostelId = Integer.parseInt(hostelIdText);
            int senderId = Integer.parseInt(senderIdText);
            int receiverId = Integer.parseInt(receiverIdText);

            // Call database handler method to add the notification
            long id = dbHandler.addNotification(hostelId, senderId, receiverId, message);

            if (id != -1) {
                // Notification added successfully
                Toast.makeText(this, "Notification sent successfully", Toast.LENGTH_SHORT).show();

                // Clear input fields
                editTextHostelId.setText("");
                editTextSenderId.setText("");
                editTextReceiverId.setText("");
                editTextMessage.setText("");
            } else {
                // Error adding notification
                Toast.makeText(this, "Failed to send notification", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show error message if any input field is empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
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