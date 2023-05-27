package com.mazbaz.tamalcoolique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mazbaz.tamalcoolique.requests.User;
import com.mazbaz.tamalcoolique.views.game.game;
import com.mazbaz.tamalcoolique.views.home.home;
import com.mazbaz.tamalcoolique.views.inventory.inventory;
import com.mazbaz.tamalcoolique.views.shop.shop;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button header_settings, nav_home, nav_games, nav_shop, nav_storage;
    static ProgressBar alcohol, hunger, urine;
    static TextView coins;

    public static User user;

    private static Fragment actualFragment;

    Intent BackGroundIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nav_home = findViewById(R.id.nav_home_button);
        nav_games = findViewById(R.id.nav_games_button);
        nav_shop = findViewById(R.id.nav_shop_button);
        nav_storage = findViewById(R.id.nav_storage_button);

        header_settings = findViewById(R.id.nav_settings_button);

        alcohol = findViewById(R.id.stats_alcoholLevel_bar);
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

        nav_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new shop());
            }
        });

        nav_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new inventory());
            }
        });


        createUser(getApplicationContext());

        header_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.removeData(getApplicationContext(), "jwt");
                setContentView(R.layout.activity_login);

                stopService(BackGroundIntent);
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        actualFragment = fragment;
    }


    private void createUser(Context context) {
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, "http://" + Utils.getData(urine.getContext(), "db") + "/api/users/me?populate[1]=items.image",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        user = gson.fromJson(response, User.class);

                        replaceFragment(new home());
                        refreshDisplayedDatas();

                        BackGroundIntent = new Intent(context, BackgroundService.class);
                        startService(BackGroundIntent);
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
        alcohol.setProgress(user.getAlcoholLevel());
        hunger.setProgress(user.getHungerLevel());
        urine.setProgress(user.getUrineLevel());

        coins.setText("Coins: " + user.getMoney().toString());

        if (actualFragment instanceof home) {
            home homeFragment = home.getInstance();
            if (homeFragment != null) {
                homeFragment.refreshView();
            }
        }

        JSONObject userData = new JSONObject();
        try {
            userData.put("alcohol_level", user.getAlcoholLevel());
            userData.put("hunger_level", user.getHungerLevel());
            userData.put("urine_level", user.getUrineLevel());
            userData.put("money", user.getMoney().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, "http://" + Utils.getData(urine.getContext(), "db") + "/api/users/" + user.getId(), userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traitement de la réponse en cas de succès
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Traitement de l'erreur en cas d'échec
                        System.out.println("Erreur lors de la mise à jour du nom d'utilisateur: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Ajout des en-têtes à la requête (Content-Type et Authorization)
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + Utils.getData(alcohol.getContext(), "jwt"));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.alcohol.getContext());
        requestQueue.add(request);
    }
}