package com.nuhkoca.udacitymoviesapp.view.detail.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.ITrailerItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.TrailerListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.model.video.VideoResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<VideoResults> mVideoResults;
    private ITrailerItemTouchListener mITrailerItemTouchListener;

    public TrailerAdapter(ITrailerItemTouchListener iTrailerItemTouchListener) {
        this.mITrailerItemTouchListener = iTrailerItemTouchListener;
        mVideoResults = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        TrailerListItemCardBinding trailerListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.trailer_list_item_card,
                parent,
                false);

        return new ViewHolder(trailerListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoResults videoResults = mVideoResults.get(position);

        holder.bindView(videoResults);
    }

    public void swapData(List<VideoResults> videoResults) {
        this.mVideoResults = videoResults;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVideoResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TrailerListItemCardBinding mTrailerListItemCardBinding;

        ViewHolder(View itemView) {
            super(itemView);

            mTrailerListItemCardBinding = DataBindingUtil.bind(itemView);
        }

        void bindView(VideoResults videoResults) {
            mTrailerListItemCardBinding.setVariable(BR.videoResults, videoResults);
            mTrailerListItemCardBinding.setVariable(BR.trailerTouchListener, mITrailerItemTouchListener);

            setCustomCardViewWidth();

            mTrailerListItemCardBinding.executePendingBindings();
        }

        void setCustomCardViewWidth() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) mTrailerListItemCardBinding.getRoot().getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            ViewGroup.LayoutParams layoutParams = mTrailerListItemCardBinding.cvTrailer.getLayoutParams();

            if (getItemCount() == 1) {
                return;
            }

            layoutParams.width = (int) (displayMetrics.widthPixels / (1.2));

            mTrailerListItemCardBinding.cvTrailer.setLayoutParams(layoutParams);
        }
    }
}