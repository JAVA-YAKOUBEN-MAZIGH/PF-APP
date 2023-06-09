package com.mazbaz.tamalcoolique.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mazbaz.tamalcoolique.MainActivity;
import com.mazbaz.tamalcoolique.R;
import com.mazbaz.tamalcoolique.Utils;
import com.mazbaz.tamalcoolique.requests.images.Image;

public class home extends Fragment {
    private View view;

    private static home instance; // Ajoutez cette ligne
    private ImageView chara;
    public static home getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        chara = view.findViewById(R.id.character);
        instance = this;
        refreshView();
        return view;
    }

    public void refreshView() {
        Integer all = MainActivity.user.getAlcoholLevel() + MainActivity.user.getHungerLevel();

        String userAvatarUrl = "";
        if (all < 10) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=8feb34&top=shortFlat&eyebrows=defaultNatural&eyes=xDizzy&mouth=vomit&clothing=collarAndSweater&clothesColor=3c4f5c";
        } else if (all >= 10 && all < 15) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=ffdbb4&top=shortFlat&eyebrows=defaultNatural&eyes=default&mouth=default&clothing=collarAndSweater&clothesColor=3c4f5c";
        } else if (all >= 15) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=ffdbb4&top=shortFlat&eyebrows=raisedExcited&eyes=hearts&mouth=smile&clothing=collarAndSweater&clothesColor=3c4f5c";
        }
        Utils.fetchSvg(getContext(), userAvatarUrl, this.chara);
    }
}