package com.mazbaz.tamalcoolique.views.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.mazbaz.tamalcoolique.requests.Item;
import com.mazbaz.tamalcoolique.requests.categories.Categorie;
import com.mazbaz.tamalcoolique.requests.categories.Categories;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class inventory extends Fragment {

    View view;

    LinearLayout inventoryContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inventory, container, false);
        inventoryContainer = view.findViewById(R.id.inventory_container);

        loadItems();
        return view;
    }

    private void addProduct(Item item) throws IOException {
        LinearLayout productLayout = new LinearLayout(getActivity());
        productLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        productLayout.setOrientation(LinearLayout.HORIZONTAL);
        productLayout.setGravity(Gravity.CENTER_VERTICAL);
        productLayout.setPadding(0, 10, 0, 0);

        // Création de l'ImageView
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));

        Picasso.get()
                .load("http://" + Utils.getData(getContext(), "db") + item.getImage().getUrl())
                .into(imageView);

        // Ajout de l'ImageView au LinearLayout
        productLayout.addView(imageView);

        LinearLayout innerLayout = new LinearLayout(getActivity());
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setWeightSum(1);

        // Création du premier TextView
        TextView textView1 = new TextView(getActivity());
        textView1.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView1.setText(item.getName());
        textView1.setTextAppearance(getActivity(), android.R.style.TextAppearance_Material_Body2);

        TextView textView2 = new TextView(getActivity());
        textView2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView2.setText("Alcohol : " + item.getAlcoholLevel() + " Hunger : " + item.getFoodLevel() + " Urine : " + item.getUrineLevel() + " Coins : " + item.getMoney());

        // Création du Button
        Button button = new Button(getActivity());

        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        button.setText("Use !");
        // Ajout des vues au LinearLayout interne
        innerLayout.addView(textView1);
        innerLayout.addView(textView2);
        innerLayout.addView(button);

        productLayout.addView(innerLayout);

        // Ajout du LinearLayout contenant l'ImageView au LinearLayout principal
        inventoryContainer.addView(productLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.consume(getActivity());
                inventoryContainer.removeView(productLayout);
            }
        });
    }

    private void loadItems() {
        for (Item item : MainActivity.user.getItems()) {
            try {
                addProduct(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}