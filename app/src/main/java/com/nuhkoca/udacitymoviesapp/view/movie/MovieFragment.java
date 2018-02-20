package com.nuhkoca.udacitymoviesapp.view.movie;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.FragmentPopularMovieBinding;
import com.nuhkoca.udacitymoviesapp.model.Result;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenter;
import com.nuhkoca.udacitymoviesapp.presenter.movie.MoviePresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.detail.MovieDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView {

    private FragmentPopularMovieBinding mFragmentPopularMovieBinding;

    public static MovieFragment getInstance(String tag) {
        MovieFragment p = new MovieFragment();

        Bundle arg =new Bundle();
        arg.putString("tag", tag);
        p.setArguments(arg);

        return p;

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
        MoviePresenter mMoviePresenter = new MoviePresenterImpl(this);

        mMoviePresenter.prepareUI(mFragmentPopularMovieBinding.rvPopularMovie);


        if (getArguments()!=null) {
            String tag = getArguments().getString("tag");
            mMoviePresenter.loadPopularMovies(getActivity(), BuildConfig.APIKEY, 1, tag);
        }
    }

    @Override
    public void onLoadingCompleted(String message) {
        SnackbarPopper.pop(mFragmentPopularMovieBinding.flPopularMovie, message);
    }

    @Override
    public void onLoadingError(String message) {
        SnackbarPopper.pop(mFragmentPopularMovieBinding.flPopularMovie, message);
    }

    @Override
    public void onActivityOpened(Result result, ImageView imageView) {
        Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
        detailIntent.putExtra("image", BuildConfig.IMAGEURLPREFIX + result.getPosterPath());
        detailIntent.putExtra("title", result.getOriginalTitle());

        startActivity(detailIntent);
    }
}