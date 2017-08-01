package com.omeerfk.dizitakibi.model;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TelevisionShow implements Parcelable {

    @SerializedName("tvShow")
    @Expose
    private TvShow tvShow;

    public TvShow getTvShow() {
        return tvShow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.tvShow, flags);
    }

    public TelevisionShow() {
    }

    protected TelevisionShow(Parcel in) {
        this.tvShow = in.readParcelable(TvShow.class.getClassLoader());
    }

    public static final Parcelable.Creator<TelevisionShow> CREATOR = new Parcelable.Creator<TelevisionShow>() {
        @Override
        public TelevisionShow createFromParcel(Parcel source) {
            return new TelevisionShow(source);
        }

        @Override
        public TelevisionShow[] newArray(int size) {
            return new TelevisionShow[size];
        }
    };
}
