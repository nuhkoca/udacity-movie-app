package com.nuhkoca.udacitymoviesapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

import java.util.List;

/**
 * Created by nuhkoca on 2/16/18.
 */

public class MovieResponse extends BaseObservable {

    @SerializedName("page")
    private String page;
    @SerializedName("total_results")
    private String totalResults;
    @SerializedName("total_pages")
    private String totalPages;
    @SerializedName("results")
    private List<Result> results;

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
}
