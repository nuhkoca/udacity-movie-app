package com.nuhkoca.udacitymoviesapp.presenter.detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivityView;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class MovieDetailActivityPresenterImpl implements MovieDetailActivityPresenter {

    private MovieDetailActivityView mMovieDetailActivityView;
    private Context context;

    public MovieDetailActivityPresenterImpl(MovieDetailActivityView mMovieDetailActivityView, Context context) {
        this.mMovieDetailActivityView = mMovieDetailActivityView;
        this.context = context;
    }

    @Override
    public void prepareUI(Bundle extras, ImageView poster, CollapsingToolbarLayout title) {
        makeLayoutTransparent();

        if (extras != null) {
            GlideApp.with(context)
                    .load(extras.getString("image"))
                    .into(poster);

            title.setTitle(extras.getString("title"));

            mMovieDetailActivityView.onDetailsLoaded();
        } else {
            mMovieDetailActivityView.onDetailsLoadingFailed(context.getString(R.string.details_not_loaded));
        }
    }

    private void makeLayoutTransparent() {
        Window w = ((Activity) context).getWindow();

        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        w.setNavigationBarColor(ContextCompat.getColor(context, R.color.colorBlack));
    }
}