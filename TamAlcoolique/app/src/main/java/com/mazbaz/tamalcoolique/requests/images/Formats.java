package com.mazbaz.tamalcoolique.requests.images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Formats {

    @SerializedName("large")
    @Expose
    private Large large;

    /**
     * No args constructor for use in serialization
     *
     */
    public Formats() {
    }

    /**
     *
     * @param large
     */
    public Formats(Large large) {
        super();
        this.large = large;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

    public Formats withLarge(Large large) {
        this.large = large;
        return this;
    }

}