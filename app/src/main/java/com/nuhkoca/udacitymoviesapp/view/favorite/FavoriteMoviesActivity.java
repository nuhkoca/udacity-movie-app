package com.nuhkoca.udacitymoviesapp.view.favorite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityFavoriteMoviesBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.favorite.MovieContract;
import com.nuhkoca.udacitymoviesapp.presenter.favorite.FavoriteMoviesActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.favorite.FavoriteMoviesActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.ColumnCalculator;
import com.nuhkoca.udacitymoviesapp.utils.SnackbarPopper;
import com.nuhkoca.udacitymoviesapp.view.favorite.adapter.FavoritesAdapter;

public class FavoriteMoviesActivity extends AppCompatActivity implements FavoriteMoviesActivityView, LoaderManager.LoaderCallbacks<Cursor> {

    private ActivityFavoriteMoviesBinding mActivityFavoriteMoviesBinding;
    private FavoriteMoviesActivityPresenter mFavoriteMoviesActivityPresenter;
    private FavoritesAdapter mFavoritesAdapter;

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

        mFavoriteMoviesActivityPresenter = new FavoriteMoviesActivityPresenterImpl(this);
        mFavoriteMoviesActivityPresenter.fetchMoviesFromDatabase();

        getSupportLoaderManager().initLoader(Constants.MOVIE_LOADER_ID, null, this);
    }

    @Override
    public void onMoviesFetchedFromDatabase() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ColumnCalculator.getOptimalNumberOfColumn(this));
        mActivityFavoriteMoviesBinding.rvFavorites.setLayoutManager(gridLayoutManager);

        mActivityFavoriteMoviesBinding.rvFavorites.setHasFixedSize(true);
        mActivityFavoriteMoviesBinding.rvFavorites.setNestedScrollingEnabled(false);

        mFavoritesAdapter = new FavoritesAdapter();
        mActivityFavoriteMoviesBinding.rvFavorites.setAdapter(mFavoritesAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(FavoriteMoviesActivity.this)
                        .setTitle(String.format(getString(R.string.dialog_title), viewHolder.itemView.getTag(Constants.VIEW_HOLDER_TAG_2)))
                        .setMessage(getString(R.string.dialog_message))
                        .setPositiveButton(getString(R.string.dialog_positive_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = Integer.toString((Integer) viewHolder.itemView.getTag(Constants.VIEW_HOLDER_TAG_1));

                                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                                uri = uri.buildUpon().appendPath(id).build();

                                getContentResolver().delete(uri, null, null);
                                getSupportLoaderManager().restartLoader(Constants.MOVIE_LOADER_ID, null, FavoriteMoviesActivity.this);
                                SnackbarPopper.pop(mActivityFavoriteMoviesBinding.llFavoriteMovies, String.format(getString(R.string.dialog_action_message), viewHolder.itemView.getTag(Constants.VIEW_HOLDER_TAG_2)));

                                mFavoritesAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(getString(R.string.dialog_negative_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFavoritesAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        }).attachToRecyclerView(mActivityFavoriteMoviesBinding.rvFavorites);
    }

    @Override
    public void showWarningText(final boolean visible) {
        int duration = 0;

        getResources().getInteger(android.R.integer.config_shortAnimTime);

        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setVisibility(visible ? View.VISIBLE : View.GONE);
        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.animate()
                .setDuration(duration)
                .alpha(visible ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setVisibility(visible ? View.VISIBLE : View.GONE);
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

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(Constants.MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DatabaseFetcher(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setVisibility(View.VISIBLE);
            mActivityFavoriteMoviesBinding.tvFavoriteMoviesErrorHolder.setText(getString(R.string.no_data_in_database));
            return;
        }

        mFavoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoritesAdapter.swapCursor(null);
    }

    private static class DatabaseFetcher extends AsyncTaskLoader<Cursor> {
        DatabaseFetcher(@NonNull Context context) {
            super(context);
        }

        Cursor mTaskData = null;

        @Override
        protected void onStartLoading() {
            if (mTaskData != null) {
                deliverResult(mTaskData);
            } else {
                forceLoad();
            }
        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            try {
                return getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

            } catch (Exception e) {
                return null;
            }
        }

        public void deliverResult(Cursor data) {
            mTaskData = data;
            super.deliverResult(data);
        }
    }
}