package com.nuhkoca.udacitymoviesapp.view.review;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityFullReviewBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.presenter.review.FullReviewActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.review.FullReviewActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.BottomSheetBuilder;
import com.nuhkoca.udacitymoviesapp.view.browser.WebBrowserActivity;

public class FullReviewActivity extends AppCompatActivity implements FullReviewActivityView {

    private ActivityFullReviewBinding mActivityFullReviewBinding;
    private FullReviewActivityPresenter mFullReviewActivityPresenter;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFullReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_review);

        mFullReviewActivityPresenter = new FullReviewActivityPresenterImpl(this);
        mFullReviewActivityPresenter.loadReview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.full_review_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onReviewLoaded() {
        setSupportActionBar(mActivityFullReviewBinding.lFullReviewToolbar.toolbar);
        setTitle("");
        mActivityFullReviewBinding.lFullReviewToolbar.tvToolbarHeader.setText(getString(R.string.full_review));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String content = getIntent().getStringExtra(Constants.REVIEW_CONTENT_EXTRA);
        author = getIntent().getStringExtra(Constants.REVIEW_AUTHOR_EXTRA);

        mActivityFullReviewBinding.tvFullReviewContent.setText(content);
        mActivityFullReviewBinding.tvFullReviewAuthor.setText(author);
    }

    @Override
    public void onBottomSheetCreated() {
        String movieName = getIntent().getStringExtra(Constants.REVIEW_MOVIE_EXTRA);

        BottomSheetBuilder.create(this,
                mActivityFullReviewBinding.bslBottomSheetFullReview,
                String.format(getString(R.string.share_extra_text_full_review), author, movieName),
                String.format(getString(R.string.share_to_full_review), author));
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onReviewOpenedOnBrowser() {
        Intent webBrowserIntent = new Intent(this, WebBrowserActivity.class);

        String reviewUrl = getIntent().getStringExtra(Constants.REVIEW_URL_EXTRA);

        webBrowserIntent.putExtra(Constants.REVIEW_URL_EXTRA, reviewUrl);

        startActivityForResult(webBrowserIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                supportFinishAfterTransition();

                setResult(RESULT_OK);
                return true;

            case R.id.share_review:
                mFullReviewActivityPresenter.initBottomSheet();
                return true;

            case R.id.report_review:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:" + getString(R.string.mail_address)));
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;

            case R.id.open_in_browser:
                mFullReviewActivityPresenter.openReviewOnBrowser();
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
        mFullReviewActivityPresenter.onDestroy();
        super.onDestroy();
    }
}