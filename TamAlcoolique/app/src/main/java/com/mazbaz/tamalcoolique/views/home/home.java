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

public class home extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView userAvatarView = view.findViewById(R.id.character);

        double all = ((MainActivity.user.getUrineLevel() + MainActivity.user.getAlcoholLevel() + MainActivity.user.getHungerLevel()) / 30.0) * 100;

    System.out.println("jauges" + all);
        String userAvatarUrl = "";
        if (all < 31) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=8feb34&top=shortFlat&eyebrows=defaultNatural&eyes=xDizzy&mouth=vomit&clothing=collarAndSweater&clothesColor=3c4f5c";
        } else if (all >= 30 && all < 70) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=ffdbb4&top=shortFlat&eyebrows=defaultNatural&eyes=default&mouth=default&clothing=collarAndSweater&clothesColor=3c4f5c";
        } else if (all >= 70) {
            userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=ffdbb4&top=shortFlat&eyebrows=raisedExcited&eyes=hearts&mouth=smile&clothing=collarAndSweater&clothesColor=3c4f5c";
        }

        Utils.fetchSvg(getContext(), userAvatarUrl, userAvatarView);
        return view;
    }

    public void refreshAvatar() {

    }
}