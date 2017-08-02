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

    @SerializedName("status")
    @Expose
    protected String status;

    @SerializedName("network")
    @Expose
    protected String network;

    @SerializedName("image_thumbnail_path")
    @Expose
    protected String imageUrl;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getNetwork() {
        return network;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
