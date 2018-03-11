package com.nuhkoca.udacitymoviesapp.model.video;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class VideoResults extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("type")
    private String type;

    public VideoResults() {}

    protected VideoResults(Parcel in) {
        key = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<VideoResults> CREATOR = new Creator<VideoResults>() {
        @Override
        public VideoResults createFromParcel(Parcel in) {
            return new VideoResults(in);
        }

        @Override
        public VideoResults[] newArray(int size) {
            return new VideoResults[size];
        }
    };

    @Bindable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(type);
    }
}
