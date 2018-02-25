package com.nuhkoca.udacitymoviesapp.model.video;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

import java.util.List;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class VideoResponse extends BaseObservable {
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("results")
    private List<VideoResults> videoResults;

    public VideoResponse() {}

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
}
