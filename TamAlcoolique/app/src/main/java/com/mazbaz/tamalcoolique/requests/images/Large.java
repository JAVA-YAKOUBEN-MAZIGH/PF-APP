package com.mazbaz.tamalcoolique.requests.images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Large {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Large() {
    }

    /**
     *
     * @param ext
     * @param name
     * @param url
     */
    public Large(String name, String ext, String url) {
        super();
        this.name = name;
        this.ext = ext;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Large withName(String name) {
        this.name = name;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Large withExt(String ext) {
        this.ext = ext;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Large withUrl(String url) {
        this.url = url;
        return this;
    }

}