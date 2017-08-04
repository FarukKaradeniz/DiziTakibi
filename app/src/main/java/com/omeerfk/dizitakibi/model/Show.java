package com.omeerfk.dizitakibi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faruk Karadeniz on 2.08.2017.
 */

public class Show implements Parcelable {

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
        dest.writeByte(this.favorited ? (byte) 1 : (byte) 0);
    }

    public Show() {
    }

    protected Show(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.network = in.readString();
        this.imageUrl = in.readString();
        this.favorited = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel source) {
            return new Show(source);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
