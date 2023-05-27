package com.mazbaz.tamalcoolique.requests;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mazbaz.tamalcoolique.MainActivity;
import com.mazbaz.tamalcoolique.Utils;
import com.mazbaz.tamalcoolique.requests.images.Image;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("alcohol_level")
    @Expose
    private Integer alcoholLevel;
    @SerializedName("food_level")
    @Expose
    private Integer foodLevel;
    @SerializedName("urine_level")
    @Expose
    private Integer urineLevel;
    @SerializedName("money")
    @Expose
    private Integer money;

    private Image image;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAlcoholLevel() {
        return alcoholLevel;
    }

    public void addAlcoholLevel(Integer alcoholLevel) {
        this.alcoholLevel += alcoholLevel;
    }

    public Integer getFoodLevel() {
        return foodLevel;
    }

    public void addFoodLevel(Integer foodLevel) {
        this.foodLevel += foodLevel;
    }

    public Integer getUrineLevel() {
        return urineLevel;
    }

    public void addUrineLevel(Integer urineLevel) {
        this.urineLevel += urineLevel;
    }

    public Integer getMoney() {
        return money;
    }

    public void addMoney(Integer money) {
        this.money += money;
    }

    public Image getImage() {
        return image;
    }

    public void buy(Context context) {
        if (MainActivity.user.getItems().stream().anyMatch(item -> item.getId() == this.getId())) {
            Toast.makeText(context, "you already have " + this.getName() + " in your inventory", Toast.LENGTH_SHORT).show();
            return;
        }

        if (MainActivity.user.getMoney() < this.price) {
            Toast.makeText(context, "you don't have enough Coins", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity.user.addMoney(-this.price);
        MainActivity.user.addItems(this);

        editUser(context, "connect");


        Toast.makeText(context, this.getName() + "purchased for " + this.price + " Coins", Toast.LENGTH_SHORT).show();
        MainActivity.refreshDisplayedDatas();

    }

    public void consume(Context context) {
        if (MainActivity.user.getItems().stream().anyMatch(item -> item.getId() == this.getId())) {
            MainActivity.user.addAlcoholLevel(this.alcoholLevel);
            MainActivity.user.addHungerLevel(this.foodLevel);
            MainActivity.user.addUrineLevel(this.urineLevel);
            MainActivity.user.addMoney(this.money);

            MainActivity.user.rmItem(this);

            editUser(context, "disconnect");

            Toast.makeText(context, this.getName() + " consumed !", Toast.LENGTH_SHORT).show();

            MainActivity.refreshDisplayedDatas();
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", alcoholLevel=" + alcoholLevel +
                ", foodLevel=" + foodLevel +
                ", urineLevel=" + urineLevel +
                ", money=" + money +
                ", image=" + image +
                '}';
    }

    private void editUser(Context context, String type) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "http://10.211.55.15:1337/api/users/" + MainActivity.user.getId();

        String requestBody = "{\n\t\"items\" : {\n\t\t\t\"" + type + "\" : [" + this.getId().toString() + "]\n\t\t}\n}\n";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traitement de la réponse
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Utils.getData(context, "jwt"));
                return headers;
            }
        };

        requestQueue.add(request);
    }
}