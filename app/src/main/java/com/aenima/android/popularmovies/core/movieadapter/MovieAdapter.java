package com.aenima.android.popularmovies.core.movieadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aenim on 19/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter {
    List<Movie> mMovieList;


    public MovieAdapter(List<Movie> movieList) {
        mMovieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_thumb_iv)
        ImageView movieThumbImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
