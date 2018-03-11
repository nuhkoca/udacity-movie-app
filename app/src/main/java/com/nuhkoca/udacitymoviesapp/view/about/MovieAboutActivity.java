package com.nuhkoca.udacitymoviesapp.view.about;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.nuhkoca.udacitymoviesapp.BuildConfig;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieAboutBinding;
import com.nuhkoca.udacitymoviesapp.presenter.about.MovieAboutActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.about.MovieAboutActivityPresenterImpl;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class MovieAboutActivity extends AppCompatActivity implements MovieAboutActivityView {

    private MovieAboutActivityPresenter mMovieAboutActivityPresenter;
    private ActivityMovieAboutBinding mMovieAboutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieAboutBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_about);

        mMovieAboutActivityPresenter = new MovieAboutActivityPresenterImpl(this);
        mMovieAboutActivityPresenter.loadAboutPage();
    }

    @Override
    public void onAboutPageLoaded() {
        setSupportActionBar(mMovieAboutBinding.lMovieAboutToolbar.toolbar);
        setTitle("");
        mMovieAboutBinding.lMovieAboutToolbar.tvToolbarHeader.setText(getString(R.string.about));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        simulateDayNight(0);

        Element adsElement = new Element();
        adsElement.setTitle(getString(R.string.scholarship_element))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW);
                        webIntent.setData(Uri.parse(getString(R.string.scholarship_link)));

                        String title = getString(R.string.chooser_title);
                        Intent chooser = Intent.createChooser(webIntent, title);
                        startActivity(chooser);
                    }
                });

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.about_description))
                .setImage(R.drawable.ic_about_page_icon)
                .addItem(new Element().setTitle(String.valueOf(String.format(getString(R.string.version), BuildConfig.VERSION_NAME))))
                .addItem(adsElement)
                .addGroup(getString(R.string.connect_with_us))
                .addEmail(getString(R.string.email))
                .addWebsite(getString(R.string.udacity_website))
                .addFacebook(getString(R.string.udacity_facebook))
                .addTwitter(getString(R.string.udacity_twitter))
                .addYoutube(getString(R.string.udacity_youtube))
                .addPlayStore(getString(R.string.udacity_play_store))
                .addInstagram(getString(R.string.udacity_instagram))
                .addGitHub(getString(R.string.github))
                .addItem(getCopyRightsElement())
                .create();

        mMovieAboutBinding.llMovieAbout.addView(aboutPage, 1);
    }

    private Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.ic_copy_right_icon);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        return copyRightsElement;
    }

    private void simulateDayNight(@SuppressWarnings("SameParameterValue") int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case android.R.id.home:
                supportFinishAfterTransition();

                setResult(RESULT_OK);
                return true;

            case R.id.license:
                new LibsBuilder()
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withAutoDetect(true)
                        .withLibraries(getResources().getStringArray(R.array.libraries_included))
                        .withExcludedLibraries(getResources().getStringArray(R.array.libraries_excluded))
                        .start(this);
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
        mMovieAboutActivityPresenter.onDestroy();
        super.onDestroy();
    }
}