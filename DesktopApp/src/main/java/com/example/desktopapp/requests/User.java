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

    @SerializedName("tasks")
    @Expose
    private List<Task> tasks;

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
                ", tasks=" + tasks +
                ", money=" + money +
                '}';
    }
}