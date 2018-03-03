package com.nuhkoca.udacitymoviesapp.model.details;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

import java.util.List;

/**
 * Created by nuhkoca on 3/3/18.
 */

public class DetailsResponse extends BaseObservable {
    @Expose
    @SerializedName("budget")
    private long budget;
    @Expose
    @SerializedName("homepage")
    private String homepage;
    @Expose
    @SerializedName("revenue")
    private long revenue;
    @Expose
    @SerializedName("runtime")
    private int runtime;
    @Expose
    @SerializedName("tagline")
    private String tagline;
    @Expose
    @SerializedName("production_companies")
    private List<ProductionCompaniesResponse> productionCompaniesResponses;
    @Expose
    @SerializedName("production_countries")
    private List<ProductionCountriesResponse> productionCountriesResponses;
    @Expose
    @SerializedName("spoken_languages")
    private List<SpokenLanguagesResponse> spokenLanguagesResponses;

    public DetailsResponse() {
    }


    @Bindable
    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
        notifyPropertyChanged(BR.budget);
    }

    @Bindable
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
        notifyPropertyChanged(BR.homepage);
    }

    @Bindable
    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
        notifyPropertyChanged(BR.revenue);
    }

    @Bindable
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
        notifyPropertyChanged(BR.runtime);
    }

    @Bindable
    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
        notifyPropertyChanged(BR.tagline);
    }

    @Bindable
    public List<ProductionCompaniesResponse> getProductionCompaniesResponses() {
        return productionCompaniesResponses;
    }

    public void setProductionCompaniesResponses(List<ProductionCompaniesResponse> productionCompaniesResponses) {
        this.productionCompaniesResponses = productionCompaniesResponses;
        notifyPropertyChanged(BR.productionCompaniesResponses);
    }

    @Bindable
    public List<ProductionCountriesResponse> getProductionCountriesResponses() {
        return productionCountriesResponses;
    }

    public void setProductionCountriesResponses(List<ProductionCountriesResponse> productionCountriesResponses) {
        this.productionCountriesResponses = productionCountriesResponses;
        notifyPropertyChanged(BR.productionCountriesResponses);
    }

    @Bindable
    public List<SpokenLanguagesResponse> getSpokenLanguagesResponses() {
        return spokenLanguagesResponses;
    }

    public void setSpokenLanguagesResponses(List<SpokenLanguagesResponse> spokenLanguagesResponses) {
        this.spokenLanguagesResponses = spokenLanguagesResponses;
        notifyPropertyChanged(BR.spokenLanguagesResponses);
    }
}