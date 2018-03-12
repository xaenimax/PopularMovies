package com.aenima.android.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MovieDetailFragment extends Fragment {
    @BindView(R.id.release_date_tv)
    TextView releaseDateTextView;

    @BindView(R.id.movie_original_title_tv)
    TextView originalTitleTextView;

    @BindView(R.id.movie_overview_tv)
    TextView overviewTextView;

    @BindView(R.id.backdrop_iv)
    ImageView movieDetailImageView;

    @BindView(R.id.main_poster_iv)
    ImageView moviePosterImageView;

    @BindView(R.id.vote_avg_tv)
    TextView voteAvgTextView;

    @BindView(R.id.movie_rating_rb)
    RatingBar movieRatingBar;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECTED_MOVIE_PARAM = "selected_movie";

    Movie mMovie;
    Unbinder bind;
    private Context context;
    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedMovie Parameter 1.
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(Movie selectedMovie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_MOVIE_PARAM, selectedMovie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(SELECTED_MOVIE_PARAM);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        releaseDateTextView.setText(getString(R.string.release_date_label) + " " + mMovie.getReleaseDate());
        //setTitle(String.valueOf(selectedMovie.getTitle()));

        if (mMovie.getOriginalTitle() != null) {
            originalTitleTextView.setText(getString(R.string.original_title_label) + " " + mMovie.getOriginalTitle());
            originalTitleTextView.setVisibility(View.VISIBLE);

        } else {
            originalTitleTextView.setVisibility(View.GONE);
        }
        overviewTextView.setText(mMovie.getOverview());
        movieRatingBar.setRating(mMovie.getVoteAvg());
        voteAvgTextView.setText(String.valueOf(mMovie.getVoteAvg()) + getString(R.string.rating_ten_string));
        Picasso.with(context).load(mMovie.getBackDropImagePath()).into(movieDetailImageView);
        Picasso.with(context).load(mMovie.getPosterImagePath()).into(moviePosterImageView);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        bind.unbind();
    }

}
