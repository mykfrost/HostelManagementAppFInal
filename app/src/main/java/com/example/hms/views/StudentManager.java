package com.example.hms.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;

public class StudentManager extends AppCompatActivity {
    SessionManager sessionManager ;
    EditText edEmail, edFullName , edRegistration , edGuardian, edPhone;
    Button addDetails;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);
        sessionManager = new SessionManager(this);
        db = new DatabaseHandler(this, sessionManager);

        edFullName = findViewById(R.id.editTextFullName);
        edEmail = findViewById(R.id.editTextEmail);
        edRegistration = findViewById(R.id.editTextRegnumber);
        edGuardian = findViewById(R.id.editTextguardian);
        edPhone= findViewById(R.id.editTextPhone);
        addDetails = findViewById(R.id.btnAddDetails);

        // Check if the user is logged in
        if (sessionManager.isLoggedIn()) {
            String fullName = sessionManager.getFullName();
            String email = sessionManager.getEmail();
            edFullName.setText( fullName);
            edEmail.setText(email);
        }

        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edFullName .getText().toString();
                String email = edEmail.getText().toString();
                String regNumber = edRegistration.getText().toString();
                String guardian = edGuardian.getText().toString();
                String phone = edPhone.getText().toString();

                db.addStudent(fullName, email , regNumber , guardian, phone);
                Toast.makeText(getApplicationContext(), "Student Details Added", Toast.LENGTH_SHORT).show();
                // Clear the EditText fields after adding details
                clearFields();
            }
        });
    }

    private void clearFields() {
        edFullName.setText("");
        edEmail.setText("");
        edRegistration.setText("");
        edGuardian.setText("");
        edPhone.setText("");
    }
}