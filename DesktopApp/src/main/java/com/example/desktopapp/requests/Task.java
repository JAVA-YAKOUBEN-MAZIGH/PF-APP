package com.example.desktopapp.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("resolved")
    @Expose
    private Boolean resolved;

    public String getName() {
        return name;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", resolved=" + resolved +
                '}';
    }
}
