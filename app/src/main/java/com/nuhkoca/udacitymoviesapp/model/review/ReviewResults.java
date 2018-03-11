package com.nuhkoca.udacitymoviesapp.model.review;

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

public class ReviewResults extends BaseObservable implements Parcelable {
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

    protected ReviewResults(Parcel in) {
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<ReviewResults> CREATOR = new Creator<ReviewResults>() {
        @Override
        public ReviewResults createFromParcel(Parcel in) {
            return new ReviewResults(in);
        }

        @Override
        public ReviewResults[] newArray(int size) {
            return new ReviewResults[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }
}