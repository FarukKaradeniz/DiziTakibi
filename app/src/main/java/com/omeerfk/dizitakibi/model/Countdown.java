package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countdown implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.season);
        dest.writeInt(this.episode);
        dest.writeString(this.name);
        dest.writeString(this.airDate);
    }

    public Countdown() {
    }

    protected Countdown(Parcel in) {
        this.season = in.readInt();
        this.episode = in.readInt();
        this.name = in.readString();
        this.airDate = in.readString();
    }

    public static final Parcelable.Creator<Countdown> CREATOR = new Parcelable.Creator<Countdown>() {
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
