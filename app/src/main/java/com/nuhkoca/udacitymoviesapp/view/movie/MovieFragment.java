package com.nuhkoca.udacitymoviesapp.view.movie;

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
import com.nuhkoca.udacitymoviesapp.databinding.FragmentPopularMovieBinding;
import com.nuhkoca.udacitymoviesapp.model.Result;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenter;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivity;
import com.nuhkoca.udacitymoviesapp.view.movie.adapter.MovieAdapter;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView, RecyclerViewItemTouchListener {

    private FragmentPopularMovieBinding mFragmentPopularMovieBinding;
    private MoviePresenter mMoviePresenter;
    private MovieAdapter mMovieAdapter;

    public static MovieFragment getInstance(String tag) {
        MovieFragment movieFragment = new MovieFragment();

        Bundle arg = new Bundle();
        arg.putString("tag", tag);
        movieFragment.setArguments(arg);

        return movieFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentPopularMovieBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_popular_movie, container, false);

        return mFragmentPopularMovieBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mMoviePresenter = new MoviePresenterImpl(this);

        if (getArguments() != null) {
            String tag = getArguments().getString("tag");
            mMoviePresenter.loadMovies(BuildConfig.APIKEY, tag);
        }
    }

    @Override
    public void onLoadingCompleted(List<Result> result) {
        mFragmentPopularMovieBinding.rvPopularMovie.setHasFixedSize(true);
        mFragmentPopularMovieBinding.rvPopularMovie.setNestedScrollingEnabled(false);

        int spanCount = 0;

        if (getActivity() != null) {
            spanCount = getActivity().getResources().getInteger(R.integer.span_count);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mFragmentPopularMovieBinding.rvPopularMovie.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mFragmentPopularMovieBinding.rvPopularMovie.setAdapter(mMovieAdapter);

        mMovieAdapter.swapData(result);
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadingFailed(String message) {
        SnackbarPopper.pop(mFragmentPopularMovieBinding.flPopularMovie, message);
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

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()),
                        imageView,
                        ViewCompat.getTransitionName(imageView));

        startActivity(detailIntent, activityOptionsCompat.toBundle());
    }
}