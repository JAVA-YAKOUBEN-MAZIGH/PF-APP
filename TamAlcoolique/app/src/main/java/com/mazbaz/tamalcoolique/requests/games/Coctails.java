package com.mazbaz.tamalcoolique.requests.games;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coctails {

    @SerializedName("data")
    @Expose
    public List<Coctail> coctails;

    public List<Coctail> getCoctails() {
        return coctails;
    }
}
