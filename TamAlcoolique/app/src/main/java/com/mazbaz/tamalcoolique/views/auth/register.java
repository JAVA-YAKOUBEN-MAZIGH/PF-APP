package com.mazbaz.tamalcoolique.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.mazbaz.tamalcoolique.R;
import com.mazbaz.tamalcoolique.Utils;
import java.io.UnsupportedEncodingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private TextView error_field;
    private Button go;
    private TextInputEditText username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Utils.getData(getApplicationContext(), "jwt") != null) {
            goToLogin();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        error_field = findViewById(R.id.error);
        go = findViewById(R.id.button);
        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        findViewById(R.id.goToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
            }
        });
    }

    private void register() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(new StringRequest(Request.Method.POST, "http://10.211.55.15:1337/api/auth/local/register?populate=*", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response);
                    String jwt = jsonNode.get("jwt").asText();

                    Utils.storeData(getApplicationContext(), "jwt", jwt);
                    goToLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if(error.networkResponse.data!=null) {
                    try {
                        String body = new String(error.networkResponse.data,"UTF-8");

                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            JsonNode jsonNode = mapper.readTree(body);
                            String message = jsonNode.get("error").get("message").asText();

                            error_field.setText(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email.getEditableText().toString()); //Add the data you'd like to send to the server.
                MyData.put("password", password.getEditableText().toString()); //Add the data you'd like to send to the server.
                MyData.put("username", username.getEditableText().toString()); //Add the data you'd like to send to the server.
                return MyData;
            }
        });
    }

    public void onPause() {
        super.onPause();
        this.finish();
    }


    private void goToLogin() {
        this.finish();
        startActivity(new Intent(register.this, login.class));
    }
}