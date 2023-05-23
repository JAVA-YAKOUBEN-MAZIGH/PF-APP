package com.mazbaz.tamalcoolique.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mazbaz.tamalcoolique.MainActivity;
import com.mazbaz.tamalcoolique.Utils;

public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
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
    @SerializedName("buyable")
    @Expose
    private Boolean buyable;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
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

    public Boolean getBuyable() {
        return buyable;
    }

    public void buy() {
        if (MainActivity.user.getMoney() >= this.price || this.buyable) {
            MainActivity.user.addMoney(-this.price);
            MainActivity.user.addItems(this);
        }
    }

    public void consume() {
        if (MainActivity.user.getItems().contains(this)) {
            MainActivity.user.addAlcoholLevel(this.alcoholLevel);
            MainActivity.user.addHungerLevel(this.foodLevel);
            MainActivity.user.addUrineLevel(this.urineLevel);

            MainActivity.user.rmItem(this);
        }
    }
}