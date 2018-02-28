package com.nuhkoca.udacitymoviesapp.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nuhkoca.udacitymoviesapp.helper.Constants;


/**
 * Created by nuhkoca on 2/24/18.
 */

public class ReviewsBindingAdapter {
    @BindingAdapter(value = {"content", "hideProgressBar", "hideHyperlinkText"})
    public static void hideProgressBarWhenDateIsLoaded(TextView content, String contentText, ProgressBar progressBar, final TextView hyperlink) {
        content.setText(Html.fromHtml(contentText));
        hyperlink.setPaintFlags(hyperlink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (content.getText().length() > 0) {
            progressBar.setVisibility(View.GONE);
            getLineCount(content, hyperlink);
        }
    }

    private static void getLineCount(final TextView content, final TextView hyperlink) {

        content.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                content.removeOnLayoutChangeListener(this);

                int lineCount = content.getLayout().getLineCount();

                hyperlink.setVisibility(lineCount == Constants.MAX_REVIEW_LINE ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }
}