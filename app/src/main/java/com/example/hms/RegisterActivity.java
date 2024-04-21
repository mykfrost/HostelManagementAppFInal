package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hms.utils.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private RequestQueue requestQueue;
    SessionManager sessionManager;
    EditText fullNameEditText, emailEditText, passwordEditText;
    TextView login ;
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEditText = findViewById(R.id.editTextEmail);
        fullNameEditText = findViewById(R.id.editTextName);
        passwordEditText = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.login);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(getApplicationContext());
    }
public void onLoginClick(View v){
        Intent login = new Intent(this , LoginActivity.class);
        startActivity(login);
}
    public void register(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String userRole = sessionManager.isAdmin() ? "admin" : "user";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("full_name", fullName);
            jsonObject.put("role", userRole);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String registerUrl = "https://pmenergies.co.ke/android/register.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, registerUrl, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (status == 0) {
                                String userRole = sessionManager.isAdmin() ? "admin" : "user";
                                user = new User( fullName, userRole);
                                sessionManager.createLoginSession(user, 3600000); // Set timeout to 1 hour (3600000 milliseconds)
                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(loginIntent);
                            } else if (status == 1) {
                                Toast.makeText(RegisterActivity.this, "Invalid Email and/or Password", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainIntent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error Missing Mandatory Parameters", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Registration Error: " + error.getMessage());

                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}