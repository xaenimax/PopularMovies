package com.aenima.android.popularmovies.core.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Movie;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    @BindView(R.id.release_date_tv)
    TextView releaseDateTextView;

    @BindView(R.id.movie_original_title_tv)
    TextView originalTitleTextView;

    @BindView(R.id.movie_overview_tv)
    TextView overviewTextView;

    @BindView(R.id.movie_detail_sv)
    ScrollView movieScrollView;

    //@BindView(R.id.backdrop_iv)
    //ImageView movieDetailImageView;

    @BindView(R.id.main_poster_iv)
    ImageView moviePosterImageView;

    @BindView(R.id.vote_avg_tv)
    TextView voteAvgTextView;

    @BindView(R.id.movie_rating_rb)
    RatingBar movieRatingBar;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECTED_MOVIE_PARAM = "selected_movie";

    private OnFavouriteSavedInteractionListener mListener;

    Movie mMovie;

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
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFavouriteSaved(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFavouriteSavedInteractionListener) {
            mListener = (OnFavouriteSavedInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFavouriteSavedInteractionListener {
        // TODO: Update argument type and name
        void onFavouriteSaved(Uri uri);
    }
}
