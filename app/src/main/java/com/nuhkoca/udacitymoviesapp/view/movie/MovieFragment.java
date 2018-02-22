package com.nuhkoca.udacitymoviesapp.view.movie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.RecyclerViewItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.FragmentMovieBinding;
import com.nuhkoca.udacitymoviesapp.model.Result;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenter;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivity;
import com.nuhkoca.udacitymoviesapp.view.movie.adapter.MovieAdapter;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView, RecyclerViewItemTouchListener {

    private FragmentMovieBinding mFragmentMovieBinding;
    private MoviePresenter mMoviePresenter;
    private MovieAdapter mMovieAdapter;

    public static MovieFragment getInstance(String tag) {
        MovieFragment movieFragment = new MovieFragment();

        Bundle arg = new Bundle();
        arg.putString("tag", tag);
        movieFragment.setArguments(arg);

        return movieFragment;
    }

    public static MovieFragment getInstance() {
        MovieFragment movieFragment = new MovieFragment();

        return movieFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentMovieBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_movie, container, false);

        return mFragmentMovieBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMoviePresenter = new MoviePresenterImpl(this);

        if (getArguments() != null) {
            String tag = getArguments().getString("tag");
            mMoviePresenter.loadMovies(BuildConfig.APIKEY, tag);
        }

        Timber.d("onViewCreated");
    }

    @Override
    public void onLoadingCompleted(List<Result> result) {
        if (getActivity() != null) {
            int spanCount = getActivity().getResources().getInteger(R.integer.span_count);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
            mFragmentMovieBinding.rvMovie.setLayoutManager(gridLayoutManager);

            mFragmentMovieBinding.rvMovie.setHasFixedSize(true);
            mFragmentMovieBinding.rvMovie.setNestedScrollingEnabled(false);

            mMovieAdapter = new MovieAdapter(this);
            mFragmentMovieBinding.rvMovie.setAdapter(mMovieAdapter);

            mMovieAdapter.swapData(result);
            mMovieAdapter.notifyDataSetChanged();

            Timber.d("onLoadingCompleted");
        }
    }

    @Override
    public void onLoadingFailed(String message) {
        SnackbarPopper.popIndefinite(mFragmentMovieBinding.flMovie, message);
    }

    @Override
    public void showProgress(final boolean visible) {
        int duration = 0;

        if (getActivity() != null) {
            duration = getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime);
        }

        mFragmentMovieBinding.pbMovie.setVisibility(visible ? View.VISIBLE : View.GONE);
        mFragmentMovieBinding.pbMovie.animate()
                .setDuration(duration)
                .alpha(visible ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFragmentMovieBinding.pbMovie.setVisibility(visible ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void onDestroy() {
        mMoviePresenter.onDestroy();
        mMovieAdapter = null;
        super.onDestroy();
    }

    @Override
    public void onItemTouched(Result result, ImageView imageView) {
        Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
        detailIntent.putExtra("movie-poster", BuildConfig.IMAGEURLPREFIX + result.getPosterPath());
        detailIntent.putExtra("movie-title", result.getOriginalTitle());
        detailIntent.putExtra("release-date", result.getReleaseDate());
        detailIntent.putExtra("vote-count", String.valueOf(result.getVoteCount()));
        detailIntent.putExtra("vote-average", String.valueOf(result.getVoteAverage()));
        detailIntent.putExtra("movie-overview", result.getOverview());

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()),
                        imageView,
                        ViewCompat.getTransitionName(imageView));

        startActivity(detailIntent, activityOptionsCompat.toBundle());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Timber.d("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Timber.d("onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }
}