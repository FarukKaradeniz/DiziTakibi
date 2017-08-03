package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countdown implements Parcelable {

    private int id;

    @SerializedName("season")
    @Expose
    private int season;
    @SerializedName("episode")
    @Expose
    private int episode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("air_date")
    @Expose
    private String airDate;

    private int showId;

    public int getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Countdown() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.season);
        dest.writeInt(this.episode);
        dest.writeString(this.name);
        dest.writeString(this.airDate);
        dest.writeInt(this.showId);
    }

    protected Countdown(Parcel in) {
        this.id = in.readInt();
        this.season = in.readInt();
        this.episode = in.readInt();
        this.name = in.readString();
        this.airDate = in.readString();
        this.showId = in.readInt();
    }

    public static final Creator<Countdown> CREATOR = new Creator<Countdown>() {
        @Override
        public Countdown createFromParcel(Parcel source) {
            return new Countdown(source);
        }

        @Override
        public Countdown[] newArray(int size) {
            return new Countdown[size];
        }
    };
}
