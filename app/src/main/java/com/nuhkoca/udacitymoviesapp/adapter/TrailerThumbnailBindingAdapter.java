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
import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;

/**
 * Created by nuhkoca on 2/25/18.
 */

public class TrailerThumbnailBindingAdapter {
    @BindingAdapter(value = {"thumbnail", "hideThumbnailProgress", "showPlayButton"})
    public static void loadTrailerThumbnails(ImageView thumbnail, String thumbnailUrl, ProgressBar progressBar, ImageView playButton) {

        thumbnailUrl = String.format(App.getInstance().getString(R.string.thumbnail_place_holder), Constants.THUMBNAIL_URL_PREFIX, thumbnailUrl);

        if (!TextUtils.isEmpty(thumbnailUrl)) {
            GlideApp.with(thumbnail.getContext())
                    .load(thumbnailUrl)
                    .listener(new MyRequestListener(progressBar, playButton))
                    .into(thumbnail);
        }

    }

    private static class MyRequestListener implements RequestListener<Drawable> {

        private ProgressBar progressBar;
        private ImageView imageView;

        MyRequestListener(ProgressBar progressBar, ImageView imageView) {
            this.progressBar = progressBar;
            this.imageView = imageView;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            this.progressBar.setVisibility(View.GONE);
            this.imageView.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            this.progressBar.setVisibility(View.GONE);
            this.imageView.setVisibility(View.VISIBLE);
            return false;
        }
    }
}