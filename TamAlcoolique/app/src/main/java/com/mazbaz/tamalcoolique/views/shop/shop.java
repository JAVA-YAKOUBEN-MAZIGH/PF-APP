package com.mazbaz.tamalcoolique.views.shop;

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

public class shop extends Fragment {

    View view;
    LinearLayout shopContainer;
    Categories categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        shopContainer = view.findViewById(R.id.inventory_container);

        loadCate();
        return view;
    }

    private void addCate(String name, String desc) {
        // Création du TextView
        TextView titleTextView = new TextView(getActivity());
        titleTextView.setText(name);
        titleTextView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Material_Display1);

        TextView descTextView = new TextView(getActivity());
        descTextView.setText(desc);


        // Ajout du TextView au LinearLayout
        shopContainer.addView(titleTextView);
        shopContainer.addView(descTextView);

        // Création du View divider2
        View dividerView = new View(getActivity());

        dividerView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.R.attr.listDivider, typedValue, true);
        int dividerDrawableRes = typedValue.resourceId;
        dividerView.setBackgroundResource(dividerDrawableRes);

        shopContainer.addView(dividerView);
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
        textView2.setText("Price : " + item.getPrice() + " Coins");

        // Création du Button
        Button button = new Button(getActivity());

        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        button.setText("Buy !");
        // Ajout des vues au LinearLayout interne
        innerLayout.addView(textView1);
        innerLayout.addView(textView2);
        innerLayout.addView(button);

        productLayout.addView(innerLayout);

        // Ajout du LinearLayout contenant l'ImageView au LinearLayout principal
        shopContainer.addView(productLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.buy(getActivity());
            }
        });
    }

    private void loadCate() {
        Volley.newRequestQueue(getActivity()).add(new StringRequest(Request.Method.GET, "http://" + Utils.getData(getActivity(), "db") + "/api/categories?populate[1]=items.image",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        categories = gson.fromJson(response, Categories.class);

                        for (Categorie categorie: categories.getCategories()) {
                            addCate(categorie.getName(), categorie.getDescription());
                            for (Item item : categorie.getItems()) {
                                try {
                                    addProduct(item);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("oups :/ shop l:89");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Authorization", "Bearer " + Utils.getData(getActivity(), "jwt"));
                return MyData;
            }
        });
    }
}