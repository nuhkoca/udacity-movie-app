package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;
import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class CustomBindingAdapter {

    @BindingAdapter(value = {"android:src", "progressBar", "floor", "title", "language"})
    public static void loadImagesFromAPIAndMakeSomeOverhaul(final ImageView imageView, String logoUrl, ProgressBar progressBar, final CardView floor, final TextView title, final TextView language) {

        logoUrl = BuildConfig.W300MAGEURLPREFIX + logoUrl;

        if (!TextUtils.isEmpty(logoUrl)) {
            GlideApp.with(imageView.getContext())
                    .load(logoUrl)
                    .listener(new MyRequestListener(progressBar))
                    .listener(GlidePalette.with(logoUrl)
                            .use(GlidePalette.Profile.VIBRANT)
                            .intoCallBack(new BitmapPalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(@Nullable Palette palette) {
                                    if (palette != null && palette.getVibrantSwatch() != null) {
                                        floor.setCardBackgroundColor(palette.getVibrantSwatch().getRgb());
                                        title.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                                        title.setTextColor(palette.getVibrantSwatch().getBodyTextColor());
                                        language.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                                        language.setTextColor(palette.getVibrantSwatch().getBodyTextColor());
                                    }else {
                                        floor.setCardBackgroundColor(ContextCompat.getColor(imageView.getContext(), R.color.colorPrimary));
                                        title.setBackgroundColor(ContextCompat.getColor(imageView.getContext(), R.color.colorPrimary));
                                        title.setTextColor(ContextCompat.getColor(imageView.getContext(), R.color.colorWhite));
                                        language.setBackgroundColor(ContextCompat.getColor(imageView.getContext(), R.color.colorPrimary));
                                        language.setTextColor(ContextCompat.getColor(imageView.getContext(), R.color.colorWhite));
                                    }
                                }
                            })
                    )
                    .into(imageView);
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
