package com.aenima.android.popularmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Video;
import com.aenima.android.popularmovies.core.model.VideoList;
import com.aenima.android.popularmovies.core.movieadapter.VideoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.aenima.android.popularmovies.fragment.MovieReviewFragment.mReviewList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieVideoFragment extends Fragment {

    private static final String RECYCLER_VIEW_STATE_KEY = "recycler_view_state_key";
    static VideoList mVideoList;
    private Parcelable recyclerViewState;
    private LinearLayoutManager linearLayoutManager;

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
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        videoRecyclerView.setLayoutManager(linearLayoutManager);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        recyclerViewState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE_KEY, recyclerViewState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY);
            setVideoList(mVideoList);
        }

    }
    public void setVideoList(VideoList videoList) {
        mVideoList = videoList;
        if (mVideoList != null) {

            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            VideoAdapter adapter = new VideoAdapter(mVideoList.videos, new VideoAdapter.OnVideoClickListener() {
                                @Override
                                public void onClick(Video video) {
                                    Uri uri =  Uri.parse(video.getYoutubeVideoUrl());
                                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                                    Log.i("Video", "Video Playing....");

                                }
                            });
                            videoRecyclerView.setAdapter(adapter);

                        }
                    }
            );

        }

    }
}
