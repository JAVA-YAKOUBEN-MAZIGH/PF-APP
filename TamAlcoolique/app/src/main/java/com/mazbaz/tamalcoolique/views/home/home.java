package com.mazbaz.tamalcoolique.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mazbaz.tamalcoolique.R;
import com.mazbaz.tamalcoolique.Utils;

public class home extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView userAvatarView = view.findViewById(R.id.character);
        String userAvatarUrl = "https://avatars.dicebear.com/v2/female/anna.svg";
        Utils.fetchSvg(getContext(), userAvatarUrl, userAvatarView);

        return view;
    }
}