package com.mazbaz.tamalcoolique.views.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mazbaz.tamalcoolique.MainActivity;
import com.mazbaz.tamalcoolique.R;

import java.util.Random;

public class OneShot extends AppCompatActivity {

    RatingBar ratingBar;
    LinearLayout game_container;

    private Random random;
    private long startTime;

    private static final int MIN_DELAY_SECONDS = 3;
    private static final int MAX_DELAY_SECONDS = 6;
    private boolean isButtonPressed = false;

    private static final int AVERAGE_REACTION_TIME = 250;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_shot);

        ratingBar = findViewById(R.id.ratingBar2);
        game_container = findViewById(R.id.game_container);

        initGame();
    }

    public void initGame() {
        game_container.setBackgroundColor(Color.TRANSPARENT);

        Button startButton = new Button(getApplicationContext());

        startButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        startButton.setText("Start");
        game_container.addView(startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void start() {
        game_container.removeAllViews();
        game_container.setBackgroundColor(Color.RED);
        ratingBar.setRating(0);
        isButtonPressed = false;

        random = new Random();
        int delaySeconds = random.nextInt(MAX_DELAY_SECONDS - MIN_DELAY_SECONDS + 1) + MIN_DELAY_SECONDS;
        int delayMilliseconds = delaySeconds * 1000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game_container.setBackgroundColor(Color.GREEN);
                startTime = System.currentTimeMillis();
                game_container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isButtonPressed) {
                            isButtonPressed = true;

                            checkDatas(System.currentTimeMillis() - startTime);
                            initGame();
                        }
                    }
                });
            }
        }, delayMilliseconds);
    }

    private void checkDatas(long elapsedTime) {
        Toast.makeText(getApplicationContext(), "Your reaction time is : " + elapsedTime + " ms", Toast.LENGTH_SHORT).show();

        float averageReactionTime = AVERAGE_REACTION_TIME;
        float maxRating = ratingBar.getNumStars();
        float rating = maxRating;

        if (elapsedTime > averageReactionTime) {
            float ratio = averageReactionTime / elapsedTime;
            rating = Math.round((maxRating * ratio) * 2) / 2.0f;
        }
        ratingBar.setRating((float) rating);

        if (rating >= 3) {
            win(getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "GG ! You are totally drunk! You won nothing !",Toast.LENGTH_SHORT).show();
        }
    }

    public void onPause() {
        super.onPause();
        this.finish();
    }

    private void win(Context context) {
        MainActivity.user.addMoney(150);
        MainActivity.user.addHungerLevel(-1);
        MainActivity.user.addAlcoholLevel(2);
        MainActivity.user.addUrineLevel(1);
        MainActivity.refreshDisplayedDatas();

        Toast.makeText(context, "Oh ! You are not drunk :( + 150 coins",Toast.LENGTH_SHORT).show();
    }
}