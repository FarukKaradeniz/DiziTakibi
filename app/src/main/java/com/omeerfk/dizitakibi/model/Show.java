package com.omeerfk.dizitakibi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faruk Karadeniz on 2.08.2017.
 */

public class Show {

    @SerializedName("id")
    @Expose
    protected int id;

    @SerializedName("name")
    @Expose
    protected String name;

    @SerializedName("network")
    @Expose
    protected String network;

    @SerializedName("image_thumbnail_path")
    @Expose
    protected String imageUrl;

    protected boolean favorited;

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNetwork() {
        return network;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
