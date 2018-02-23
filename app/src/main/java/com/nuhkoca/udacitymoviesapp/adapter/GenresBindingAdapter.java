package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.util.SparseArray;
import android.widget.TextView;

import com.nuhkoca.udacitymoviesapp.R;

import java.util.List;

/**
 * Created by nuhkoca on 2/23/18.
 */

public class GenresBindingAdapter {
    @BindingAdapter(value = {"app:genreValue"})
    public static void setGenres(TextView genre, List<Integer> genres) {
        SparseArray<String> allGenres = new SparseArray<>();

        int length = genre.getResources().getIntArray(R.array.genresId).length;

        for (int i = 0; i < length; i++) {
            allGenres.put(genre.getResources().getIntArray(R.array.genresId)[i],
                    genre.getResources().getStringArray(R.array.genresLabel)[i]);
        }

        int count = 0;

        if (genres.size() > 0) {
            for (int i = 0; i < allGenres.size(); i++) {
                for (int k = 0; k < genres.size(); k++) {
                    if (genres.get(k) == allGenres.keyAt(i)) {
                        count++;
                        if (count != genres.size()) {
                            genre.append(allGenres.valueAt(i) + ", ");
                        }else {
                            genre.append(allGenres.valueAt(i));
                        }
                    }
                }
            }
        }
    }
}
