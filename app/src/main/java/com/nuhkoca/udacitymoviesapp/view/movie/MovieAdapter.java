package com.nuhkoca.udacitymoviesapp.view.movie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.RecyclerViewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.MovieListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Result> mResults;
    private RecyclerViewItemTouchListener mRecyclerViewItemTouchListener;

    public MovieAdapter(RecyclerViewItemTouchListener recyclerViewItemTouchListener) {
        this.mRecyclerViewItemTouchListener = recyclerViewItemTouchListener;
        this.mResults = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        MovieListItemCardBinding movieListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.movie_list_item_card,
                parent,
                false);

        return new ViewHolder(movieListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = mResults.get(position);

        holder.bindView(result);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void swapData(List<Result> results) {
        mResults = results;

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private MovieListItemCardBinding mMovieListItemCardBinding;

        ViewHolder(View itemView) {
            super(itemView);

            mMovieListItemCardBinding = DataBindingUtil.bind(itemView);
        }

        void bindView(Result result) {
            mMovieListItemCardBinding.setVariable(BR.result, result);
            mMovieListItemCardBinding.setVariable(BR.listener, mRecyclerViewItemTouchListener);

            ViewCompat.setTransitionName(mMovieListItemCardBinding.ivMoviePoster,
                    "movie-poster" + getAdapterPosition());

            mMovieListItemCardBinding.executePendingBindings();
        }
    }
}