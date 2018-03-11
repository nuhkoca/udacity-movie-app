package com.nuhkoca.udacitymoviesapp.model.video;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

import java.util.List;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class VideoResponse extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("results")
    private List<VideoResults> videoResults;

    public VideoResponse() {}

    protected VideoResponse(Parcel in) {
        id = in.readInt();
        videoResults = in.createTypedArrayList(VideoResults.CREATOR);
    }

    public static final Creator<VideoResponse> CREATOR = new Creator<VideoResponse>() {
        @Override
        public VideoResponse createFromParcel(Parcel in) {
            return new VideoResponse(in);
        }

        @Override
        public VideoResponse[] newArray(int size) {
            return new VideoResponse[size];
        }
    };

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public List<VideoResults> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResults> videoResults) {
        this.videoResults = videoResults;
        notifyPropertyChanged(BR.videoResults);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(videoResults);
    }
}
