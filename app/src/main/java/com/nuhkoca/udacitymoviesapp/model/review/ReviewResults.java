package com.nuhkoca.udacitymoviesapp.model.review;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class ReviewResults extends BaseObservable {
    @Expose
    @SerializedName("author")
    private String author;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("url")
    private String url;

    public ReviewResults() {}

    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }
}