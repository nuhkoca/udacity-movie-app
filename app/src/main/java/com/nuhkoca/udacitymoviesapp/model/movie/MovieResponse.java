package com.nuhkoca.udacitymoviesapp.model.movie;

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
    private byte page;
    @Expose
    @SerializedName("total_results")
    private int totalResults;
    @Expose
    @SerializedName("total_pages")
    private int totalPages;
    @Expose
    @SerializedName("results")
    private List<Results> results;

    public MovieResponse() {}

    protected MovieResponse(Parcel in) {
        page = in.readByte();
        totalResults = in.readInt();
        totalPages = in.readInt();
        results = in.createTypedArrayList(Results.CREATOR);
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

    @Bindable
    public int getPage() {
        return page;
    }

    public void setPage(byte page) {
        this.page = page;
        notifyPropertyChanged(BR.page);
    }

    @Bindable
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        notifyPropertyChanged(BR.totalResults);
    }

    @Bindable
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        notifyPropertyChanged(BR.totalPages);
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
        notifyPropertyChanged(BR.results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(page);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
        dest.writeTypedList(results);
    }
}