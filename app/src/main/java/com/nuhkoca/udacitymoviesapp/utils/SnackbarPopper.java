package com.nuhkoca.udacitymoviesapp.utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.nuhkoca.udacitymoviesapp.R;

/**
 * Created by nuhkoca on 2/19/18.
 */

public class SnackbarPopper {
    public static void pop(View view, String message){
        Snackbar snackbar = Snackbar.make(view,
                message,
                Snackbar.LENGTH_SHORT);

        View currentView = snackbar.getView();
        currentView.setBackgroundColor(ContextCompat.getColor(currentView.getContext(), R.color.colorPrimary));

        TextView currentText = currentView.findViewById(android.support.design.R.id.snackbar_text);
        currentText.setTextSize(16);
        currentText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));

        snackbar.show();
    }

    public static void popIndefinite(View view, String message){
        Snackbar snackbar = Snackbar.make(view,
                message,
                Snackbar.LENGTH_INDEFINITE);

        View currentView = snackbar.getView();
        currentView.setBackgroundColor(ContextCompat.getColor(currentView.getContext(), R.color.colorPrimary));

        TextView currentText = currentView.findViewById(android.support.design.R.id.snackbar_text);
        currentText.setTextSize(16);
        currentText.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));

        snackbar.show();
    }
}
