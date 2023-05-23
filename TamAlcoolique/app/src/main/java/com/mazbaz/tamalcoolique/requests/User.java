package com.mazbaz.tamalcoolique.requests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("confirmed")
    @Expose
    private Boolean confirmed;
    @SerializedName("blocked")
    @Expose
    private Boolean blocked;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("alcohol_level")
    @Expose
    private Integer alcoholLevel;
    @SerializedName("hunger_level")
    @Expose
    private Integer hungerLevel;
    @SerializedName("urine_level")
    @Expose
    private Integer urineLevel;
    @SerializedName("role")
    @Expose
    private Role role;
    @SerializedName("items")
    @Expose
    private List<Item> items;

    @SerializedName("money")
    @Expose
    private Integer money;
    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Object getAlcoholLevel() {
        return alcoholLevel;
    }

    public void addAlcoholLevel(Integer alcoholLevel) {
        this.alcoholLevel += alcoholLevel;
    }

    public Integer getHungerLevel() {
        return hungerLevel;
    }

    public void addHungerLevel(Integer hungerLevel) {
        this.hungerLevel += hungerLevel;
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

    public void addMoney(Integer value) {
        this.money += value;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItems(Item item) {
        this.items.add(item);
    }

    public void rmItem(Item item){
        this.items.remove(item);
    }
}