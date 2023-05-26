package com.mazbaz.tamalcoolique.requests.categories;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mazbaz.tamalcoolique.requests.Item;

public class Categorie {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("identifier")
    @Expose
    public String identifier;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("items")
    @Expose
    public List<Item> items;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
