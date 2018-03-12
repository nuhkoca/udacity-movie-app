package com.nuhkoca.udacitymoviesapp.view.browser;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebViewClient;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityWebBrowserBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.presenter.browser.WebBrowserActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.browser.WebBrowserActivityPresenterImpl;

public class WebBrowserActivity extends AppCompatActivity implements WebBrowserActivityView {

    private ActivityWebBrowserBinding mActivityWebBrowserBinding;
    private WebBrowserActivityPresenter mWebBrowserActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWebBrowserBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_browser);

        mWebBrowserActivityPresenter = new WebBrowserActivityPresenterImpl(this);
        mWebBrowserActivityPresenter.prepareUI();
        mWebBrowserActivityPresenter.openReview();
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
    public void onUIPrepared() {
        setSupportActionBar(mActivityWebBrowserBinding.lFullReview.toolbar);
        setTitle("");
        mActivityWebBrowserBinding.lFullReview.tvToolbarHeader.setText(getString(R.string.full_review));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBrowserOpened() {
        mActivityWebBrowserBinding.wvFullReview.getSettings().setJavaScriptEnabled(true);
        mActivityWebBrowserBinding.wvFullReview.setWebViewClient(new WebViewClient());

        String reviewUrl = getIntent().getStringExtra(Constants.REVIEW_URL_EXTRA);

        mActivityWebBrowserBinding.wvFullReview.loadUrl(reviewUrl);
    }

    @Override
    protected void onDestroy() {
        mWebBrowserActivityPresenter.onDestroy();
        super.onDestroy();
    }
}