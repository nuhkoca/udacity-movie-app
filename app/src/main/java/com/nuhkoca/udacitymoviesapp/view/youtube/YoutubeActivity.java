package com.nuhkoca.udacitymoviesapp.view.youtube;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityYoutubeBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;

import timber.log.Timber;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

    private YouTubePlayer mYoutubePlayer;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityYoutubeBinding mActivityYoutubeBinding = DataBindingUtil.setContentView(this, R.layout.activity_youtube);

        mActivityYoutubeBinding.ypvVideo.initialize(BuildConfig.YOUTUBEAPIKEY, this);

        extras = getIntent().getExtras();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        mYoutubePlayer = youTubePlayer;

        mYoutubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        mYoutubePlayer.setFullscreen(true);
        mYoutubePlayer.setPlayerStateChangeListener(this);

        if (!b) {
            if (extras != null) {
                String videoId = extras.getString(Constants.YOUTUBE_VIDEO_ID);

                mYoutubePlayer.cueVideo(videoId);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Timber.d("onInitializationFailure%s ", youTubeInitializationResult.name());
    }

    @Override
    public void onLoading() {
        Timber.d("onLoading");
    }

    @Override
    public void onLoaded(String s) {
        Timber.d("onLoaded");
    }

    @Override
    public void onAdStarted() {
        Timber.d("onAdStarted");

    }

    @Override
    public void onVideoStarted() {
        Timber.d("onVideoStarted");

    }

    @Override
    public void onVideoEnded() {
        Timber.d("onVideoEnded");

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Timber.d("onError%s ", errorReason.name());
    }

    @Override
    protected void onDestroy() {
        mYoutubePlayer.release();
        mYoutubePlayer = null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:

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
}