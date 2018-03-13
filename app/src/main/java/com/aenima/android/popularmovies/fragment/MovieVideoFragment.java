package com.aenima.android.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.VideoList;
import com.aenima.android.popularmovies.core.movieadapter.VideoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieVideoFragment extends Fragment {

    @BindView(R.id.video_list_rv)
    RecyclerView videoRecyclerView;

    Unbinder bind;

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
        View view = inflater.inflate(R.layout.fragment_movie_video, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        bind.unbind();
    }

    public void setVideoList(final VideoList videoList) {
        if (videoList != null) {

            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            VideoAdapter adapter = new VideoAdapter(videoList.videos);
                            videoRecyclerView.setAdapter(adapter);

                        }
                    }
            );

        }

    }
}
