package com.nuhkoca.udacitymoviesapp.view.movie;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.FragmentPopularMovieBinding;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenter;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMovieFragment extends Fragment implements MovieView {

    private FragmentPopularMovieBinding mFragmentPopularMovieBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentPopularMovieBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_popular_movie, container, false);

        return mFragmentPopularMovieBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mFragmentPopularMovieBinding.rvMovies.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mFragmentPopularMovieBinding.rvMovies.setLayoutManager(gridLayoutManager);

        MovieAdapter mMovieAdapter = new MovieAdapter();
        mFragmentPopularMovieBinding.rvMovies.setAdapter(mMovieAdapter);

        MoviePresenter mMoviePresenter = new MoviePresenterImpl(this);
        mMoviePresenter.loadMovies(mMovieAdapter, BuildConfig.APIKEY, 1);
    }

    @Override
    public void onLoadingCompleted() {

    }

    @Override
    public void onLoadingError() {

    }
}
