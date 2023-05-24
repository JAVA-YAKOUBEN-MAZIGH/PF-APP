package com.mazbaz.tamalcoolique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mazbaz.tamalcoolique.requests.User;
import com.mazbaz.tamalcoolique.views.game.game;
import com.mazbaz.tamalcoolique.views.home.home;

public class MainActivity extends AppCompatActivity {
    Button nav_home, nav_games, nav_shop, nav_storage, header_settings;
    public static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nav_home = findViewById(R.id.nav_home_button);
        nav_games = findViewById(R.id.nav_games_button);
        header_settings = findViewById(R.id.nav_settings_button);

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
        replaceFragment(new home());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}