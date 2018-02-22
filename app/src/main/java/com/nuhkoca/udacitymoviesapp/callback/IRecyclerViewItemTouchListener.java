package com.nuhkoca.udacitymoviesapp.callback;

import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.model.Result;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface IRecyclerViewItemTouchListener {
    void onItemTouched(Result result, ImageView imageView);
}
