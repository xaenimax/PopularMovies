package com.aenima.android.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.VideoList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieVideoFragment extends Fragment {

    private VideoList videoList;

    public MovieVideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MovieVideoFragment.
     */
    public static MovieVideoFragment newInstance() {
        MovieVideoFragment fragment = new MovieVideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_video, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setVideoList(VideoList videoList) {
        this.videoList = videoList;
    }
}
