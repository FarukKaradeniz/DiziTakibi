package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowShort {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("permalink")
    @Expose
    private String permalink;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("image_thumbnail_path")
    @Expose
    private String imageUrl;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}