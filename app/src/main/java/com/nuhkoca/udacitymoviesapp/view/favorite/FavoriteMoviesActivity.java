package com.nuhkoca.udacitymoviesapp.view.favorite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityFavoriteMoviesBinding;
import com.nuhkoca.udacitymoviesapp.presenter.favorite.FavoriteMoviesActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.favorite.FavoriteMoviesActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.ColumnCalculator;
import com.nuhkoca.udacitymoviesapp.view.favorite.adapter.FavoritesAdapter;

public class FavoriteMoviesActivity extends AppCompatActivity implements FavoriteMoviesActivityView {

    private ActivityFavoriteMoviesBinding mActivityFavoriteMoviesBinding;
    private FavoriteMoviesActivityPresenter mFavoriteMoviesActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFavoriteMoviesBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_movies);
        setSupportActionBar(mActivityFavoriteMoviesBinding.lFavoriteMoviesToolbar.toolbar);
        setTitle("");
        mActivityFavoriteMoviesBinding.lFavoriteMoviesToolbar.ibSort.setVisibility(View.GONE);
        mActivityFavoriteMoviesBinding.lFavoriteMoviesToolbar.tvToolbarHeader.setText(getString(R.string.my_favorites));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mFavoriteMoviesActivityPresenter = new FavoriteMoviesActivityPresenterImpl(this, this);
        mFavoriteMoviesActivityPresenter.fetchMoviesFromDatabase();
    }

    @Override
    public void onMoviesFetchedFromDatabase(Cursor movieCursor) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ColumnCalculator.getOptimalNumberOfColumn(this));
        mActivityFavoriteMoviesBinding.rvFavorites.setLayoutManager(gridLayoutManager);

        mActivityFavoriteMoviesBinding.rvFavorites.setHasFixedSize(true);
        mActivityFavoriteMoviesBinding.rvFavorites.setNestedScrollingEnabled(false);

        FavoritesAdapter mFavoritesAdapter = new FavoritesAdapter(movieCursor);
        mActivityFavoriteMoviesBinding.rvFavorites.setAdapter(mFavoritesAdapter);
    }

    @Override
    public void onMoviesLoadingFromDatabaseFailed(String message) {
        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setText(message);
    }

    @Override
    public void showProgress(final boolean visible) {
        int duration = 0;

        getResources().getInteger(android.R.integer.config_shortAnimTime);


        mActivityFavoriteMoviesBinding.pbFavorites.setVisibility(visible ? View.VISIBLE : View.GONE);
        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setVisibility(visible ? View.GONE: View.VISIBLE);

        mActivityFavoriteMoviesBinding.pbFavorites.animate()
                .setDuration(duration)
                .alpha(visible ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mActivityFavoriteMoviesBinding.pbFavorites.setVisibility(visible ? View.VISIBLE : View.GONE);
                        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setVisibility(visible ? View.GONE: View.VISIBLE);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                supportFinishAfterTransition();

                setResult(RESULT_OK);
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mFavoriteMoviesActivityPresenter.onDestroy();
        super.onDestroy();
    }
}