package com.example.desktopapp.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tasks {
    @SerializedName("data")
    @Expose
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "tasks=" + tasks.toString() +
                '}';
    }
}
