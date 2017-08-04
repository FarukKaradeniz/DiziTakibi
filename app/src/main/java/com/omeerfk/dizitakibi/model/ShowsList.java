package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowsList implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.total);
        dest.writeInt(this.page);
        dest.writeInt(this.pages);
        dest.writeList(this.tvShows);
    }

    public ShowsList() {
    }

    protected ShowsList(Parcel in) {
        this.total = in.readString();
        this.page = in.readInt();
        this.pages = in.readInt();
        this.tvShows = new ArrayList<Show>();
        in.readList(this.tvShows, Show.class.getClassLoader());
    }

    public static final Parcelable.Creator<ShowsList> CREATOR = new Parcelable.Creator<ShowsList>() {
        @Override
        public ShowsList createFromParcel(Parcel source) {
            return new ShowsList(source);
        }

        @Override
        public ShowsList[] newArray(int size) {
            return new ShowsList[size];
        }
    };
}
