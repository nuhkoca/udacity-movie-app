package com.nuhkoca.udacitymoviesapp.model.details;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.udacitymoviesapp.BR;

/**
 * Created by nuhkoca on 3/3/18.
 */

public class SpokenLanguagesResponse extends BaseObservable {
    @Expose
    @SerializedName("name")
    private String name;

    public SpokenLanguagesResponse() {
    }


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
