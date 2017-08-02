package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShow extends Show implements Parcelable {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("countdown")
    @Expose
    private Countdown countdown;

    @SerializedName("genres")
    @Expose
    private List<String> genres;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl.replace("thumbnail", "full");
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public List<String> getGenres() {
        return genres;
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
        dest.writeString(this.description);
        dest.writeString(this.status);
        dest.writeString(this.network);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.countdown, flags);
        dest.writeStringList(this.genres);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.status = in.readString();
        this.network = in.readString();
        this.imageUrl = in.readString();
        this.countdown = in.readParcelable(Countdown.class.getClassLoader());
        this.genres = in.createStringArrayList();
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
}
