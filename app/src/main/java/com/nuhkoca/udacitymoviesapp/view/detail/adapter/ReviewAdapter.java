package com.nuhkoca.udacitymoviesapp.view.detail.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.IReviewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.ReviewListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;

import java.util.List;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewResults> mReviewResults;
    private IReviewItemTouchListener mIReviewItemTouchListener;

    public ReviewAdapter(List<ReviewResults> mReviewResults, IReviewItemTouchListener mIReviewItemTouchListener) {
        this.mReviewResults = mReviewResults;
        this.mIReviewItemTouchListener = mIReviewItemTouchListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        ReviewListItemCardBinding reviewListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.review_list_item_card,
                parent,
                false);

        return new ViewHolder(reviewListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewResults reviewResults = mReviewResults.get(position);

        holder.bindView(reviewResults);
    }

    public void swapData(List<ReviewResults> reviewResults) {
        this.mReviewResults = reviewResults;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ReviewListItemCardBinding mReviewListItemCardBinding;

        ViewHolder(View itemView) {
            super(itemView);

            mReviewListItemCardBinding = DataBindingUtil.bind(itemView);
        }

        void bindView(ReviewResults reviewResults) {
            mReviewListItemCardBinding.setVariable(BR.reviewResults, reviewResults);
            mReviewListItemCardBinding.setVariable(BR.reviewTouchListener, mIReviewItemTouchListener);

            setCustomCardViewWidth();

            mReviewListItemCardBinding.executePendingBindings();
        }

        void setCustomCardViewWidth() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) mReviewListItemCardBinding.getRoot().getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            ViewGroup.LayoutParams layoutParams = mReviewListItemCardBinding.cvReview.getLayoutParams();

            if (getItemCount() == 1) {
                return;
            }

            if (displayMetrics.widthPixels <= 1440) {
                layoutParams.width = (int) (displayMetrics.widthPixels / (1.2));
            } else {
                layoutParams.width = (int) (displayMetrics.widthPixels / (1.5));
            }

            mReviewListItemCardBinding.cvReview.setLayoutParams(layoutParams);
        }
    }
}