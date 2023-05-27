package com.mazbaz.tamalcoolique.views.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazbaz.tamalcoolique.R;
import com.mazbaz.tamalcoolique.views.auth.login;
import com.mazbaz.tamalcoolique.views.auth.register;

public class game extends Fragment {

   View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        view.findViewById(R.id.game1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GuessMyDrink.class));
            }

        });

        view.findViewById(R.id.game2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OneShot.class));
            }
        });

        return view;
    }
}