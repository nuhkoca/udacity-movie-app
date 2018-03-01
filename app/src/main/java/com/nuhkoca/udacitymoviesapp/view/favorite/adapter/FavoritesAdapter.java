package com.nuhkoca.udacitymoviesapp.view.favorite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.FavoriteListItemCardBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.model.favorite.MovieContract;
import com.nuhkoca.udacitymoviesapp.module.GlideApp;

/**
 * Created by nuhkoca on 2/27/18.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private Cursor mCursor;

    public FavoritesAdapter(){}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        FavoriteListItemCardBinding favoriteListItemCardBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.favorite_list_item_card,
                parent,
                false);

        return new ViewHolder(favoriteListItemCardBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        int idIndex = mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry._ID));
        String movieName = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME));
        String movieGenre = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_GENRE));
        String movieImage = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE));


        holder.itemView.setTag(Constants.VIEW_HOLDER_TAG_1, idIndex);
        holder.itemView.setTag(Constants.VIEW_HOLDER_TAG_2, movieName);
        holder.bindView(movieName, movieGenre, movieImage);
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) {
            return;
        }

        this.mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private FavoriteListItemCardBinding mFavoriteListItemCardBinding;

        ViewHolder(View itemView) {
            super(itemView);

            mFavoriteListItemCardBinding = DataBindingUtil.bind(itemView);
        }

        void bindView(String movieName, String movieGenre, String movieImage) {
            GlideApp.with(mFavoriteListItemCardBinding.ivFavoritePoster.getContext())
                    .load(movieImage)
                    .into(mFavoriteListItemCardBinding.ivFavoritePoster);

            mFavoriteListItemCardBinding.tvFavoriteTitle.setText(movieName);
            mFavoriteListItemCardBinding.tvFavoriteGenre.setText(movieGenre);

            mFavoriteListItemCardBinding.executePendingBindings();
        }
    }
}