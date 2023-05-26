package com.mazbaz.tamalcoolique.requests.categories;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {
    @SerializedName("data")
    @Expose
    public List<Categorie> categories;

    public List<Categorie> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories.toString() +
                '}';
    }
}
