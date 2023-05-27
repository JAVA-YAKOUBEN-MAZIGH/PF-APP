package com.mazbaz.tamalcoolique.views.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mazbaz.tamalcoolique.MainActivity;
import com.mazbaz.tamalcoolique.R;
import com.mazbaz.tamalcoolique.Utils;
import com.mazbaz.tamalcoolique.requests.games.Coctail;
import com.mazbaz.tamalcoolique.requests.games.Coctails;
import com.mazbaz.tamalcoolique.requests.games.Ingredient;
import com.mazbaz.tamalcoolique.views.auth.login;
import com.mazbaz.tamalcoolique.views.home.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GuessMyDrink extends AppCompatActivity {
    Coctails coctails;
    Integer level, max_level, points;
    RatingBar ratingBar;
    LinearLayout game_container;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_my_drink);

        ratingBar = findViewById(R.id.ratingBar);
        name = findViewById(R.id.coctail_name);
        game_container = findViewById(R.id.game_container);
        points = 0;

        getCoctails(this);
    }

    private void getCoctails(Context context) {
        Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET, "http://" + Utils.getData(context, "db") + "/api/coctails?populate=*",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        coctails = gson.fromJson(response, Coctails.class);
                        level = 0;
                        max_level = coctails.getCoctails().size();

                        play(context);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("oups :/ guessMyDrink l:89");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Authorization", "Bearer " + Utils.getData(context, "jwt"));
                return MyData;
            }
        });


    }

    private void updateRateBar() {
        ratingBar.setNumStars(max_level);
        ratingBar.setRating(points);
        ratingBar.setMax(max_level);
    }

    private void play(Context context) {
        if (level >= max_level) {
            if (points == max_level) {
                win(context);
            } else {
                Toast.makeText(context, "Ahahah you'r bad !",Toast.LENGTH_SHORT).show();
            }
            finish();
            startActivity(new Intent(context, MainActivity.class));
            return;
        }

        updateRateBar();
        game_container.removeAllViews();

        Coctail coctail = coctails.getCoctails().get(level);

        name.setText(coctail.getName());

        for (Ingredient ingredient : shuffleIngredients(coctail.getIngredients(), coctail.getBadIngredient())) {
            // Création du Button
            Button button = new Button(getApplicationContext());

            button.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(ingredient.getName());
            game_container.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ingredient selectedIngredient = ingredient;

                    if (selectedIngredient.getId() == coctail.getBadIngredient().getId()) {
                        Toast.makeText(context, "Good job!", Toast.LENGTH_SHORT).show();
                        points++;
                    } else {
                        Toast.makeText(context, "HAHAHA you're bad!", Toast.LENGTH_SHORT).show();
                    }

                    level++;
                    play(context);
                }
            });
        }
    }

    public static List<Ingredient> shuffleIngredients(List<Ingredient> ingredients, Ingredient badIngredient) {
        List<Ingredient> shuffledIngredients = new ArrayList<>(ingredients);

        Collections.shuffle(shuffledIngredients);

        Random random = new Random();
        int randomIndex = random.nextInt(shuffledIngredients.size() + 1);

        shuffledIngredients.add(randomIndex, badIngredient);

        return shuffledIngredients;
    }

    public void onPause() {
        super.onPause();
        this.finish();
    }

    private void win(Context context) {
        MainActivity.user.addMoney(100);
        MainActivity.user.addHungerLevel(-3);
        MainActivity.user.addAlcoholLevel(10);
        MainActivity.user.addUrineLevel(7);
        MainActivity.refreshDisplayedDatas();

        Toast.makeText(context, "GG ! You are totally drunk!",Toast.LENGTH_SHORT).show();
    }
}
