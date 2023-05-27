package com.mazbaz.tamalcoolique.requests.games;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coctail {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("Name")
    @Expose
    public String name;

    @SerializedName("ingredients")
    @Expose
    public List<Ingredient> ingredients;
    @SerializedName("bad_ingredient")
    @Expose
    public Ingredient badIngredient;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Ingredient getBadIngredient() {
        return badIngredient;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}


