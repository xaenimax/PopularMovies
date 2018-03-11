package com.aenima.android.popularmovies.core.movieadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aenim on 07/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<Review> mReviewList;

    public ReviewAdapter(List<Review> reviewList){
        mReviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviewList.get(position);
        holder.author.setText(review.author);
        holder.content.setText(review.content);

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author_tv)
        public TextView author;

        @BindView(R.id.review_description_tv)
        public TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
