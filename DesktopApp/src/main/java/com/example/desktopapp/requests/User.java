package com.example.desktopapp.requests;

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

    public Integer getAlcoholLevel() {
        return alcoholLevel;
    }


    public Integer getHungerLevel() {
        return hungerLevel;
    }


    public Integer getUrineLevel() {
        return urineLevel;
    }

    public void addUrineLevel(Integer urineLevel) {
        this.urineLevel += urineLevel;

        // Limiter la valeur à un maximum de +10 et un minimum de 0
        if (this.urineLevel > 10) {
            this.urineLevel = 10;
        } else if (this.urineLevel < 0) {
            this.urineLevel = 0;
        }
    }

    public void addHungerLevel(Integer hungerLevel) {
        this.hungerLevel += hungerLevel;

        // Limiter la valeur à un maximum de +10 et un minimum de 0
        if (this.hungerLevel > 10) {
            this.hungerLevel = 10;
        } else if (this.hungerLevel < 0) {
            this.hungerLevel = 0;
        }
    }

    public void addAlcoholLevel(Integer alcoholLevel) {
        this.alcoholLevel += alcoholLevel;

        // Limiter la valeur à un maximum de +10 et un minimum de 0
        if (this.alcoholLevel > 10) {
            this.alcoholLevel = 10;
        } else if (this.alcoholLevel < 0) {
            this.alcoholLevel = 0;
        }
    }
    public Integer getMoney() {
        return money;
    }

    public void addMoney(Integer value) {
        this.money += value;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", provider='" + provider + '\'' +
                ", confirmed=" + confirmed +
                ", blocked=" + blocked +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", alcoholLevel=" + alcoholLevel +
                ", hungerLevel=" + hungerLevel +
                ", urineLevel=" + urineLevel +
                ", money=" + money +
                '}';
    }
}