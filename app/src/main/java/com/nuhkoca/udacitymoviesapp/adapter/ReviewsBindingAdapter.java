package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class ReviewsBindingAdapter {
    @BindingAdapter(value = {"hideProgressBar"})
    public static void hideProgressBarWhenDateIsLoaded(TextView content, ProgressBar progressBar) {
        if (content.getText().length() > 0) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
