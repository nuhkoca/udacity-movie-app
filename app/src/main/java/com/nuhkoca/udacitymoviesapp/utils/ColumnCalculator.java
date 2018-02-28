package com.nuhkoca.udacitymoviesapp.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by nuhkoca on 2/28/18.
 */

public class ColumnCalculator {
    public static int getOptimalNumberOfColumn(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context != null) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        int widthDivider = 500;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}