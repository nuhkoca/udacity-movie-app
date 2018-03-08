package com.nuhkoca.udacitymoviesapp.view.movie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BR;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.IMovieItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.MovieListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.model.movie.Results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Results> mResults;
    private IMovieItemTouchListener mIMovieItemTouchListener;

    public MovieAdapter(IMovieItemTouchListener IMovieItemTouchListener) {
        this.mIMovieItemTouchListener = IMovieItemTouchListener;
        this.mResults = new ArrayList<>();
    }

    public MovieAdapter(List<Results> mResults, IMovieItemTouchListener mIMovieItemTouchListener) {
        this.mResults = mResults;
        this.mIMovieItemTouchListener = mIMovieItemTouchListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        MovieListItemCardBinding movieListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.movie_list_item_card,
                parent,
                false);

        return new ViewHolder(movieListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Results results = mResults.get(position);

        holder.bindView(results);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void swapData(List<Results> results) {
        mResults = results;

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private MovieListItemCardBinding mMovieListItemCardBinding;

        ViewHolder(View itemView) {
            super(itemView);

            mMovieListItemCardBinding = DataBindingUtil.bind(itemView);
        }

        void bindView(Results results) {
            mMovieListItemCardBinding.setVariable(BR.results, results);
            mMovieListItemCardBinding.setVariable(BR.movieTouchListener, mIMovieItemTouchListener);

            ViewCompat.setTransitionName(mMovieListItemCardBinding.ivMoviePoster,
                    "movie-poster" + getAdapterPosition());

            mMovieListItemCardBinding.executePendingBindings();
        }
    }
}