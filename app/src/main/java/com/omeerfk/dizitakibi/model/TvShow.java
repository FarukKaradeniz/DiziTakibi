package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShow extends Show implements Parcelable {

    @SerializedName("countdown")
    @Expose
    private Countdown countdown;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getImageUrl() {
        return imageUrl.replace("thumbnail", "full");
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public void setCountdown(Countdown countdown) {
        this.countdown = countdown;
    }

    public String getNetwork() {
        return network;
    }

    public TvShow() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.network);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.countdown, flags);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.network = in.readString();
        this.imageUrl = in.readString();
        this.countdown = in.readParcelable(Countdown.class.getClassLoader());
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getShowInfo(){
        return getName() + "\n" + getCountdown().getAirDate();
    }
}
