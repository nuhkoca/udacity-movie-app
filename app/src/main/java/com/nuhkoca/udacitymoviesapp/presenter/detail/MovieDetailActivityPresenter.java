package com.nuhkoca.udacitymoviesapp.presenter.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

/**
 * Created by nuhkoca on 2/19/18.
 */

public interface MovieDetailActivityPresenter {
    void prepareUI(Bundle extras, ImageView poster, CollapsingToolbarLayout title);
}