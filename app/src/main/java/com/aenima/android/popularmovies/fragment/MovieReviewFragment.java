package com.aenima.android.popularmovies.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.ReviewList;
import com.aenima.android.popularmovies.core.movieadapter.ReviewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieReviewFragment extends Fragment {
    private static final String RECYCLER_VIEW_STATE_KEY = "recycler_view_state_key";

    static ReviewList mReviewList = null;

    @BindView(R.id.review_list_rv)
    RecyclerView reviewListRecyclerView;

    private Unbinder bind;
    private Parcelable recyclerViewState;
    LinearLayoutManager linearLayoutManager;

    public MovieReviewFragment() {
        // Required empty public constructor
    }

    public static MovieReviewFragment newInstance() {
        MovieReviewFragment fragment = new MovieReviewFragment();

        return fragment;
    }

    public void setReviewList(ReviewList reviewList) {
        mReviewList = reviewList;
        if (mReviewList != null) {

            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            ReviewAdapter adapter = new ReviewAdapter(mReviewList.reviews);
                            reviewListRecyclerView.setAdapter(adapter);
                            if(recyclerViewState != null) {
                                linearLayoutManager.onRestoreInstanceState(recyclerViewState);
                            }

                        }
                    }
            );

        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        reviewListRecyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_review, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
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
            setReviewList(mReviewList);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(bind != null)
            bind.unbind();
    }

}
