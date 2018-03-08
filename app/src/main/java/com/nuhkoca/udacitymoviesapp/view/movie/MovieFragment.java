package com.nuhkoca.udacitymoviesapp.view.movie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.nuhkoca.udacitymoviesapp.callback.IMovieItemTouchListener;
import com.nuhkoca.udacitymoviesapp.databinding.FragmentMovieBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.movie.Results;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenter;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.ColumnCalculator;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivity;
import com.nuhkoca.udacitymoviesapp.view.movie.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView, IMovieItemTouchListener {

    private FragmentMovieBinding mFragmentMovieBinding;
    private MoviePresenter mMoviePresenter;
    private MovieAdapter mMovieAdapter;

    private static final String MOVIE_STATE_KEY = "movie-model";
    private List<Results> results = new ArrayList<>();

    private boolean isRotated = false;

    public static MovieFragment getInstance(String tag) {
        MovieFragment movieFragment = new MovieFragment();

        Bundle arg = new Bundle();
        arg.putString("tag", tag);
        movieFragment.setArguments(arg);

        return movieFragment;
    }

    public static MovieFragment getInstance() {
        return new MovieFragment();
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
        if (getArguments() != null) {
            mMoviePresenter = new MoviePresenterImpl(this);

            String tag = getArguments().getString("tag");
            mMoviePresenter.loadMovies(BuildConfig.APIKEY, tag);
        }
    }

    @Override
    public void onLoadingCompleted(List<Results> results) {
        if (getActivity() != null) {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), ColumnCalculator.getOptimalNumberOfColumn(getActivity()));
            mFragmentMovieBinding.rvMovie.setLayoutManager(gridLayoutManager);

            mFragmentMovieBinding.rvMovie.setHasFixedSize(true);
            mFragmentMovieBinding.rvMovie.setNestedScrollingEnabled(false);

            mMovieAdapter = new MovieAdapter(results, this);
            mFragmentMovieBinding.rvMovie.setAdapter(mMovieAdapter);
            mMovieAdapter.swapData(results);
        }
    }

    @Override
    public void onLoadingFailed(String message) {
        mFragmentMovieBinding.tvMovieErrorHolder.setText(message);
    }

    @Override
    public void showProgress(final boolean visible) {
        int duration = 0;

        if (getActivity() != null) {
            duration = getActivity().getResources().getInteger(android.R.integer.config_longAnimTime);
        }

        mFragmentMovieBinding.pbMovie.setVisibility(visible ? View.VISIBLE : View.GONE);
        mFragmentMovieBinding.tvMovieErrorHolder.setVisibility(visible ? View.VISIBLE : View.GONE);

        mFragmentMovieBinding.pbMovie.animate()
                .setDuration(duration)
                .alpha(visible ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFragmentMovieBinding.tvMovieErrorHolder.setVisibility(visible ? View.VISIBLE : View.GONE);

                        if (mFragmentMovieBinding.tvMovieErrorHolder.getVisibility() == View.VISIBLE) {
                            mFragmentMovieBinding.pbMovie.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        //mMoviePresenter.onDestroy();
        //mMovieAdapter = null;
        super.onDestroy();
    }

    @Override
    public void onMovieItemTouched(Results results, ImageView imageView, String genre) {
        Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
        detailIntent.putExtra(Constants.MOVIE_MODEL_TAG, results);
        detailIntent.putExtra(Constants.GENRE_TAG, genre);

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()),
                        imageView,
                        ViewCompat.getTransitionName(imageView));

        startActivityForResult(detailIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE, activityOptionsCompat.toBundle());
    }
}