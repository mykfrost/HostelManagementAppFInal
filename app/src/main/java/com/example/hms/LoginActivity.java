package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    User user ;
    private static final String TAG = "LoginActivity";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        sessionManager = new SessionManager(getApplicationContext());


    }
    public void onLoginClick(View view) {
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
                    try {
                        int status = response.getInt("status");
                        String message = response.getString("message");

                        if (status == 0) {
                            String fullName = response.getString("full_name");
                            String eMail = response.getString("email");
                            String userRole = response.getString("user_role");

                            User user = new User(eMail, fullName, userRole);
                            sessionManager.createLoginSession(user);
                            Toast.makeText(getApplicationContext(), "Welcome, " + fullName, Toast.LENGTH_SHORT).show();
                            // Redirect to next activity or perform other actions
                            Intent register = new Intent(getApplicationContext(),MainActivity.class);
                            Toast.makeText(getApplicationContext(), "Welcome, " + fullName, Toast.LENGTH_SHORT).show();
                            ;
                            register.putExtra("full_names", fullName);
                            register.putExtra("email", eMail);
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

    public void reg(View v){
        Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(register);
    }
}