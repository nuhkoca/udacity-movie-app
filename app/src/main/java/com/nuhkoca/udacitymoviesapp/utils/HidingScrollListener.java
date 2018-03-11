package com.nuhkoca.udacitymoviesapp.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nuhkoca.udacitymoviesapp.helper.Constants;

/**
 * Created by nuhkoca on 3/10/18.
 */

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstCompletelyVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        //show views if first item is first visible position and views are hidden
        if (firstCompletelyVisibleItem == 0) {
            if (!controlsVisible) {
                onShow();
                controlsVisible = true;
            }
        } else {
            if (scrolledDistance > Constants.HIDE_THRESHOLD && controlsVisible) { //scrolling down
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;

            } else if (scrolledDistance < -Constants.HIDE_THRESHOLD && !controlsVisible) { //scrolling up
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();
}