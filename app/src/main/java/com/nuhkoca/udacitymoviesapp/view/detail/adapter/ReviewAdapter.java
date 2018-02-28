package com.nuhkoca.udacitymoviesapp.view.detail.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.IReviewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.ReviewListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.model.review.ReviewResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuhkoca on 2/24/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewResults> mReviewResults;
    private IReviewItemTouchListener mIReviewItemTouchListener;

    public ReviewAdapter(IReviewItemTouchListener iReviewItemTouchListener) {
        this.mIReviewItemTouchListener = iReviewItemTouchListener;
        mReviewResults = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        ReviewListItemCardBinding reviewListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.review_list_item_card,
                parent,
                false);

        return new ViewHolder(reviewListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

        private void bindView(ReviewResults reviewResults) {
            mReviewListItemCardBinding.setVariable(BR.reviewResults, reviewResults);
            mReviewListItemCardBinding.setVariable(BR.reviewTouchListener, mIReviewItemTouchListener);

            mReviewListItemCardBinding.executePendingBindings();
        }
    }
}
