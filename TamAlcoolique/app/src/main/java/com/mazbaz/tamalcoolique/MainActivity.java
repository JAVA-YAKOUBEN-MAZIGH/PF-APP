package com.mazbaz.tamalcoolique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mazbaz.tamalcoolique.requests.User;
import com.mazbaz.tamalcoolique.views.game.game;
import com.mazbaz.tamalcoolique.views.home.home;
import com.mazbaz.tamalcoolique.views.shop.shop;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button header_settings, nav_home, nav_games, nav_shop, nav_storage;
    static ProgressBar alchool;
    static ProgressBar hunger;
    static ProgressBar urine;
    static TextView coins;

    static Fragment actualFrag;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nav_home = findViewById(R.id.nav_home_button);
        nav_games = findViewById(R.id.nav_games_button);
        header_settings = findViewById(R.id.nav_settings_button);

        alchool = findViewById(R.id.stats_alcoholLevel_bar);
        hunger = findViewById(R.id.stats_hunger_bar);
        urine = findViewById(R.id.stats_urine_bar);

        coins = findViewById(R.id.stats_coins_title);

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new home());
            }
        });

        nav_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new game());
            }
        });

        header_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.removeData(getApplicationContext(), "jwt");
            }
        });

        replaceFragment(new shop());
        createUser();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        actualFrag = fragment;
    }

    public void refreshFragment() {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(actualFrag);
        ft.attach(actualFrag);
        ft.commit();
    }

    private void createUser() {
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, "http://10.211.55.15:1337/api/users/me?populate=*",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        user = gson.fromJson(response, User.class);
                        refreshDisplayedDatas();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("oups :/ MainActivity l:89");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Authorization", "Bearer " + Utils.getData(getApplicationContext(), "jwt"));
                return MyData;
            }
        });
    }

    public static void refreshDisplayedDatas() {
        alchool.setProgress(user.getAlcoholLevel());
        hunger.setProgress(user.getHungerLevel());
        urine.setProgress(user.getUrineLevel());

        coins.setText("Coins: " + user.getMoney().toString());
    }
}