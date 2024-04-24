package com.example.hms.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.hms.utils.Notification;
import com.example.hms.utils.User;

public class CreateNotification extends AppCompatActivity {

    private EditText editTextMessage, editTextReceiverId;
    private TextView textViewSenderId;
    private Button butonViewFeedback, buttonSendFeedback;

    private SessionManager sessionManager;
    private DatabaseHandler dbHandler;
    private int senderId; // Fetch sender ID from SessionManager
    private int hostelId;

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
        // Initialize views and SessionManager
        editTextMessage = findViewById(R.id.editTextMessage);
        editTextReceiverId = findViewById(R.id.editTextReceiverId);
        textViewSenderId = findViewById(R.id.textViewSenderId);
        buttonSendFeedback = findViewById(R.id.buttonSendFeedback);
        butonViewFeedback = findViewById(R.id.buttonViewFeedback);

        sessionManager = new SessionManager(this);
        dbHandler = new DatabaseHandler(this, sessionManager);

        if (sessionManager.isLoggedIn()) {
            senderId = sessionManager.getUserID(); // Fetch sender ID
            textViewSenderId.setText("Sender ID: " + senderId); // Display sender ID
            if(sessionManager.isAdmin()){
                butonViewFeedback.setVisibility(View.VISIBLE);
            }
        }

        // Button click listener to send feedback
        buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });

        butonViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),ViewNotifications.class);
                startActivity(next);
            }
        });
    }

    private void sendFeedback() {
        String receiverIdText = editTextReceiverId.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!TextUtils.isEmpty(receiverIdText) && !TextUtils.isEmpty(message)) {
            int receiverId = Integer.parseInt(receiverIdText);

            // Create a Notification object
            Notification feedback = new Notification();
            feedback.setHostelId(hostelId);
            feedback.setSenderId(senderId); // Set sender ID
            feedback.setReceiverId(receiverId);
            feedback.setMessage(message);

            // Call database handler method to add the feedback
            long id = dbHandler.addNotification(feedback);

            if (id != -1) {
                Toast.makeText(this, "Feedback sent successfully", Toast.LENGTH_SHORT).show();
                editTextMessage.setText("");
                editTextReceiverId.setText("");
            } else {
                Toast.makeText(this, "Failed to send feedback", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHandler != null) {
            dbHandler.close();
        }
    }

}