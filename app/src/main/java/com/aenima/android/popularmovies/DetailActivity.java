package com.aenima.android.popularmovies;

import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aenima.android.popularmovies.core.MovieDBAPI;
import com.aenima.android.popularmovies.core.database.MovieDbHelper;
import com.aenima.android.popularmovies.fragment.MovieDetailFragment;
import com.aenima.android.popularmovies.fragment.MovieReviewFragment;
import com.aenima.android.popularmovies.core.model.Movie;
import com.aenima.android.popularmovies.core.model.ReviewList;
import com.aenima.android.popularmovies.core.model.VideoList;
import com.aenima.android.popularmovies.fragment.MovieVideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity{
    MovieDbHelper movieDbHelper = new MovieDbHelper(this);
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.container)
    ViewPager mViewPager;

    Movie selectedMovie;
    ReviewList reviewList;
    VideoList videoList;

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if(getIntent() != null && getIntent().hasExtra(getString(R.string.EXTRA_MOVIE_KEY))) {
            selectedMovie = getIntent().getParcelableExtra(getString(R.string.EXTRA_MOVIE_KEY));
        }
        // Give the TabLayout the ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        if(movieDbHelper.isMovieFavourite(selectedMovie.getIdString())) {
            fab.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            fab.setImageResource(android.R.drawable.btn_star_big_off);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieDbHelper.isMovieFavourite(selectedMovie.getIdString())) {

                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    movieDbHelper.removeFavouriteMovie(selectedMovie);
                    Snackbar.make(view, getString(R.string.removed_from_favourites), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    movieDbHelper.addFavouriteMovie(selectedMovie);
                    Snackbar.make(view, getString(R.string.added_to_favourites), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        });

    }
    private void showErrorMessage() {
        Toast.makeText(this, R.string.no_connection_error, Toast.LENGTH_LONG).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[] tabTitles = new String[]{getString(R.string.detail_tab_title), getString(R.string.review_tab_title), getString(R.string.video_tab_title)};
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return MovieDetailFragment.newInstance(selectedMovie);
                case 1:
                    final MovieReviewFragment fragment = MovieReviewFragment.newInstance();
                    if(reviewList == null) {
                        Call<ReviewList> reviewListCall = MovieDBAPI.getMovieReviews(selectedMovie.getIdString());
                        reviewListCall.enqueue(new Callback<ReviewList>() {
                            @Override
                            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                                reviewList = response.body();
                                fragment.setReviewList(reviewList);
                            }

                            @Override
                            public void onFailure(Call<ReviewList> call, Throwable t) {
                                Log.e(this.getClass().getName(), "Errore : " +t.getLocalizedMessage());
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        showErrorMessage();
                                    }
                                });

                            }
                        });
                    }else {
                        fragment.setReviewList(reviewList);
                    }
                    return fragment;

                case 2:
                    final MovieVideoFragment videoFragment = MovieVideoFragment.newInstance();
                    if(videoList == null) {
                        Call<VideoList> videoListCall = MovieDBAPI.getMovieVideos(selectedMovie.getIdString());
                        videoListCall.enqueue(new Callback<VideoList>() {
                            @Override
                            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                                videoList = response.body();
                                videoFragment.setVideoList(videoList);
                            }

                            @Override
                            public void onFailure(Call<VideoList> call, Throwable t) {
                                Log.e(this.getClass().getName(), "Errore : " +t.getLocalizedMessage());
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        showErrorMessage();
                                    }
                                });

                            }
                        });
                    }else {
                        videoFragment.setVideoList(videoList);
                    }
                    return videoFragment;

                default:
                        return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
