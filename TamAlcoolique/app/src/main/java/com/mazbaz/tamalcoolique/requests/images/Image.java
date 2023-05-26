package com.mazbaz.tamalcoolique.requests.images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("formats")
    @Expose
    private Formats formats;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("mime")
    @Expose
    private String mime;
    @SerializedName("size")
    @Expose
    private Double size;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Image withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Image withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Image withFormats(Formats formats) {
        this.formats = formats;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Image withExt(String ext) {
        this.ext = ext;
        return this;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Image withMime(String mime) {
        this.mime = mime;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Image withSize(Double size) {
        this.size = size;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image withUrl(String url) {
        this.url = url;
        return this;
    }
}