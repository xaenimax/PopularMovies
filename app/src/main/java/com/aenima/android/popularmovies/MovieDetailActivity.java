package com.aenima.android.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aenima.android.popularmovies.core.model.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.release_date_tv)
    TextView releaseDateTextView;

    @BindView(R.id.movie_original_title_tv)
    TextView originalTitleTextView;

    @BindView(R.id.movie_overview_tv)
    TextView overviewTextView;

    @BindView(R.id.movie_detail_sv)
    NestedScrollView movieScrollView;

    @BindView(R.id.backdrop_iv)
    ImageView movieDetailImageView;

    @BindView(R.id.main_poster_iv)
    ImageView moviePosterImageView;

    @BindView(R.id.vote_avg_tv)
    TextView voteAvgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Movie selectedMovie = getIntent().getParcelableExtra(getString(R.string.EXTRA_MOVIE_KEY));
        releaseDateTextView.setText(getString(R.string.release_date_label) + " " + selectedMovie.getReleaseDate());
        setTitle(String.valueOf(selectedMovie.getTitle()));

        if(selectedMovie.getOriginalTitle() != null) {
            originalTitleTextView.setText(getString(R.string.original_title_label) + " " + selectedMovie.getOriginalTitle());
            originalTitleTextView.setVisibility(View.VISIBLE);

        }else {
            originalTitleTextView.setVisibility(View.GONE);
        }
        overviewTextView.setText(selectedMovie.getOverview());
        voteAvgTextView.setText(String.valueOf(selectedMovie.getVoteAvg())+ "/10");
        Picasso.with(this).load(selectedMovie.getBackDropImagePath()).into(movieDetailImageView);
        Picasso.with(this).load(selectedMovie.getPosterImagePath()).into(moviePosterImageView);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }
}
