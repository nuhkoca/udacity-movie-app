package com.nuhkoca.udacitymoviesapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

import java.util.List;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class MovieResponse extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("page")
    private String page;
    @Expose
    @SerializedName("total_results")
    private String totalResults;
    @Expose
    @SerializedName("total_pages")
    private String totalPages;
    @Expose
    @SerializedName("results")
    private List<Result> results;

    public MovieResponse() {
    }

    private MovieResponse(Parcel in) {
        page = in.readString();
        totalResults = in.readString();
        totalPages = in.readString();
        results = in.createTypedArrayList(Result.CREATOR);
    }

    @Bindable
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
        notifyPropertyChanged(BR.page);
    }

    @Bindable
    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
        notifyPropertyChanged(BR.totalResults);
    }

    @Bindable
    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
        notifyPropertyChanged(BR.totalPages);
    }

    @Bindable
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyPropertyChanged(BR.results);
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(page);
        dest.writeString(totalResults);
        dest.writeString(totalPages);
        dest.writeTypedList(results);
    }
}