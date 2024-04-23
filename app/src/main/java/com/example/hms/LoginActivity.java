package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hms.Singleton.VolleySingleton;
import com.example.hms.utils.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    SessionManager sessionManager ;
    private ProgressBar progressBar;

    User user ;
    private static final String TAG = "LoginActivity";
    private RequestQueue requestQueue;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.btnLogin);
        sessionManager = new SessionManager(getApplicationContext());

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            // If logged in, redirect to MainActivity
            redirectToMain();
        }
        // Initialize progress bar
        progressBar = findViewById(R.id.progressBar);
        sessionManager = new SessionManager(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
                clearFields();
            }
        });

    }

    public void onLoginClick() {
        showProgressBar();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String loginUrl = "https://pmenergies.co.ke/android/login.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, jsonObject,
                response -> {
                   hideProgressBar();
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");
                        Log.d(TAG, "Login Response: " + response.toString());
                        if (status == 0) {
                            updateSessionData(response); // Update session data with response fields
                            String fullName = response.getString("full_name");
                            String userRole = response.getString("user_role");
                            Toast.makeText(getApplicationContext(), "Welcome, " + fullName, Toast.LENGTH_SHORT).show();
                            // Redirect to next activity or perform other actions
                            Intent register = new Intent(getApplicationContext(),MainActivity.class);
                            register.putExtra("full_names", fullName);
                            register.putExtra("role", userRole);
                            startActivity(register);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
                            startActivity(register);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            hideProgressBar();
            Log.e("LoginError", "Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(), "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        //Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void updateSessionData(JSONObject response) {
        try {
            int userId = response.getInt("user_id");
            String fullName = response.getString("full_name");
            String userRole = response.getString("user_role");


            // Create User object with fetched data
            User user = new User(fullName, userRole);

            // Update session manager with user data
            sessionManager.createLoginSession(user, 3600000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void redirectToMain() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
public  void clearFields(){
        emailEditText.setText("");
        passwordEditText.setText("");
}
    public void reg(View v){
        Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(register);
    }
}