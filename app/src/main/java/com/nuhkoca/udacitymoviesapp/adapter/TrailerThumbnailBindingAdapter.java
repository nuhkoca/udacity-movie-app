package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;

/**
 * Created by nuhkoca on 2/25/18.
 */

public class TrailerThumbnailBindingAdapter {
    @BindingAdapter(value = {"thumbnail", "hideThumbnailProgress"})
    public static void loadTrailerThumbnails(ImageView thumbnail, String thumbnailUrl, ProgressBar progressBar) {

        thumbnailUrl = Constants.THUMBNAIL_URL_PREFIX + thumbnailUrl + "/0.jpg";

        if (!TextUtils.isEmpty(thumbnailUrl)) {
            GlideApp.with(thumbnail.getContext())
                    .load(thumbnailUrl)
                    .listener(new MyRequestListener(progressBar))
                    .into(thumbnail);
        }

    }

    private static class MyRequestListener implements RequestListener<Drawable> {

        private ProgressBar progressBar;

        MyRequestListener(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            this.progressBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            this.progressBar.setVisibility(View.GONE);
            return false;
        }
    }
}