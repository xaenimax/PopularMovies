package com.aenima.android.popularmovies.core.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.MovieDBAPI;
import com.aenima.android.popularmovies.core.model.ReviewList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieReviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOVIE_ID_PARAM = "movie_id_param";

    private String movieId;

    public MovieReviewFragment() {
        // Required empty public constructor
    }


    public static MovieReviewFragment newInstance(String movieId) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putString(MOVIE_ID_PARAM, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(MOVIE_ID_PARAM);
        }
        Call<ReviewList> reviewListCall = MovieDBAPI.getMovieReviews(movieId);
        reviewListCall.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                /*
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showErrorMessage();
                    }
                });
             */
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_review, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
