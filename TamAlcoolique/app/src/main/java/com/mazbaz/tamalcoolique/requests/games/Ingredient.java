package com.mazbaz.tamalcoolique.requests.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("Name")
    @Expose
    public String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
