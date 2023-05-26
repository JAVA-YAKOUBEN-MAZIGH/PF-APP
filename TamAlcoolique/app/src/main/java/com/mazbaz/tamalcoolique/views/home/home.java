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
        String userAvatarUrl = "https://api.dicebear.com/6.x/avataaars/svg?style=circle&skinColor=ffdbb4&top=shortFlat&eyebrows=defaultNatural&eyes=default&mouth=default&clothing=collarAndSweater&clothesColor=3c4f5c";

        Utils.fetchSvg(getContext(), userAvatarUrl, userAvatarView);
        return view;
    }
}