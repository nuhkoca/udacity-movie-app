package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class NumberBindingAdapter {

    @BindingAdapter("android:text")
    public static void setText(TextView genres, int value) {
        String setValue = genres.getText().toString();

        setValue = String.valueOf(genres.getText().toString());

        genres.setText(setValue);
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static String getText(TextView genres) {
        return String.valueOf(genres.getText().toString());
    }
}
