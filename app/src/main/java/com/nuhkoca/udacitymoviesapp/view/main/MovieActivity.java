package com.nuhkoca.udacitymoviesapp.view.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.nuhkoca.udacitymoviesapp.R;
import com.nuhkoca.udacitymoviesapp.callback.IHidingViewsListener;
import com.nuhkoca.udacitymoviesapp.databinding.ActivityMovieBinding;
import com.nuhkoca.udacitymoviesapp.helper.Constants;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenter;
import com.nuhkoca.udacitymoviesapp.presenter.main.MovieActivityPresenterImpl;
import com.nuhkoca.udacitymoviesapp.utils.SmartFragmentStatePagerAdapter;
import com.nuhkoca.udacitymoviesapp.view.about.MovieAboutActivity;
import com.nuhkoca.udacitymoviesapp.view.favorite.FavoriteMoviesActivity;
import com.nuhkoca.udacitymoviesapp.view.movie.MovieFragment;

public class MovieActivity extends AppCompatActivity implements MovieActivityView, BottomNavigationView.OnNavigationItemSelectedListener, IHidingViewsListener {

    private ActivityMovieBinding mActivityMovieBinding;
    private MovieActivityPresenter mMovieActivityPresenter;

    private long mBackPressed;
    private MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        mMovieActivityPresenter = new MovieActivityPresenterImpl(this);

        mMovieActivityPresenter.beautifyUI(getString(R.string.app_name));
        mMovieActivityPresenter.prepareViewPager();

        mActivityMovieBinding.bnvMovieActivity.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onViewPagerReady() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mActivityMovieBinding.vpMovieActivity.setAdapter(adapter);
        mActivityMovieBinding.vpMovieActivity.setOffscreenPageLimit(Constants.VIEW_PAGER_SIZE);
        mActivityMovieBinding.vpMovieActivity.setPageTransformer(true, new DepthPageTransformer());

        mActivityMovieBinding.vpMovieActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                } else {
                    mActivityMovieBinding.bnvMovieActivity.getMenu().getItem(0).setChecked(false);
                }
                mActivityMovieBinding.bnvMovieActivity.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mActivityMovieBinding.bnvMovieActivity.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onUIBeautified(String title) {
        setSupportActionBar(mActivityMovieBinding.lMovieActivity.toolbar);
        setTitle("");

        mActivityMovieBinding.lMovieActivity.tvToolbarHeader.setText(title);
    }

    @Override
    public void onBackPressed() {
        int timeDelay = getResources().getInteger(R.integer.delay_in_seconds_to_close);

        if (mBackPressed + timeDelay > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.twice_press_to_exit),
                    Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        if (mMovieActivityPresenter != null) {
            mMovieActivityPresenter.onDestroy();
        }
        mActivityMovieBinding.vpMovieActivity.setAdapter(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case R.id.menu_report:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:" + getString(R.string.mail_address)));
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;

            case R.id.menu_favorite:
                Intent favoriteIntent = new Intent(MovieActivity.this, FavoriteMoviesActivity.class);
                startActivityForResult(favoriteIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
                return true;

            case R.id.menu_about:
                Intent aboutIntent = new Intent(MovieActivity.this, MovieAboutActivity.class);
                startActivityForResult(aboutIntent, Constants.CHILD_ACTIVITY_REQUEST_CODE);
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case R.id.nav_popular:
                mActivityMovieBinding.vpMovieActivity.setCurrentItem(0);
                break;

            case R.id.nav_top_rated:
                mActivityMovieBinding.vpMovieActivity.setCurrentItem(1);
                break;

            case R.id.nav_upcoming:
                mActivityMovieBinding.vpMovieActivity.setCurrentItem(2);
                break;

            case R.id.nav_now_playing:
                mActivityMovieBinding.vpMovieActivity.setCurrentItem(3);
                break;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onHideViews() {
        //mActivityMovieBinding.lMovieActivity.aplMain.animate().translationY(
                //-mActivityMovieBinding.lMovieActivity.aplMain.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        mActivityMovieBinding.bnvMovieActivity.animate().translationY(
                mActivityMovieBinding.bnvMovieActivity.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void onShowViews() {
        //mActivityMovieBinding.lMovieActivity.aplMain.animate().translationY(0)
                //.setInterpolator(new DecelerateInterpolator(2));

        mActivityMovieBinding.bnvMovieActivity.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {
        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MovieFragment.getInstance(getString(R.string.popular_tag));

                case 1:
                    return MovieFragment.getInstance(getString(R.string.top_rated_tag));

                case 2:
                    return MovieFragment.getInstance(getString(R.string.upcoming_tag));

                case 3:
                    return MovieFragment.getInstance(getString(R.string.now_playing_tag));

                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (object instanceof MovieFragment) {
                return POSITION_UNCHANGED;
            } else {
                return POSITION_NONE;
            }
        }

        @Override
        public Fragment getRegisteredFragment(int position) {
            switch (position) {
                case 0:
                    return MovieFragment.getInstance(getString(R.string.popular_tag));

                case 1:
                    return MovieFragment.getInstance(getString(R.string.top_rated_tag));

                case 2:
                    return MovieFragment.getInstance(getString(R.string.upcoming_tag));

                case 3:
                    return MovieFragment.getInstance(getString(R.string.now_playing_tag));

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return Constants.VIEW_PAGER_SIZE;
        }
    }
}