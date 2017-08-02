package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowsList {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("pages")
    @Expose
    private int pages;
    @SerializedName("tv_shows")
    @Expose
    private List<Show> tvShows;

    public String getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<Show> getShows() {
        return tvShows;
    }
}
