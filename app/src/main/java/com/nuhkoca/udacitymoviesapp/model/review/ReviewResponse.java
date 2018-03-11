package com.nuhkoca.udacitymoviesapp.model.review;

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

public class ReviewResponse extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("pages")
    private byte pages;
    @Expose
    @SerializedName("total_pages")
    private byte totalPages;
    @Expose
    @SerializedName("total_results")
    private byte totalResults;
    @Expose
    @SerializedName("results")
    private List<ReviewResults> reviewResults;

    public ReviewResponse() {}

    protected ReviewResponse(Parcel in) {
        pages = in.readByte();
        totalPages = in.readByte();
        totalResults = in.readByte();
    }

    public static final Creator<ReviewResponse> CREATOR = new Creator<ReviewResponse>() {
        @Override
        public ReviewResponse createFromParcel(Parcel in) {
            return new ReviewResponse(in);
        }

        @Override
        public ReviewResponse[] newArray(int size) {
            return new ReviewResponse[size];
        }
    };

    @Bindable
    public byte getPages() {
        return pages;
    }

    public void setPages(byte pages) {
        this.pages = pages;
        notifyPropertyChanged(BR.pages);
    }

    @Bindable
    public byte getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(byte totalPages) {
        this.totalPages = totalPages;
        notifyPropertyChanged(BR.totalPages);
    }

    @Bindable
    public byte getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(byte totalResults) {
        this.totalResults = totalResults;
        notifyPropertyChanged(BR.totalResults);
    }

    @Bindable
    public List<ReviewResults> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<ReviewResults> results) {
        this.reviewResults = results;
        notifyPropertyChanged(BR.reviewResults);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(pages);
        dest.writeByte(totalPages);
        dest.writeByte(totalResults);
    }
}