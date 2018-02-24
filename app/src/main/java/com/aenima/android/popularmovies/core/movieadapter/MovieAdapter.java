package com.aenima.android.popularmovies.core.movieadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aenim on 19/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    List<Movie> mMovieList;
    OnMovieClickListener movieClickListener;

    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        mMovieList = movieList;
        movieClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Context context = holder.movieThumbImageView.getContext();
        Picasso.with(context).load(mMovieList.get(position).getPosterImagePath()).into(holder.movieThumbImageView);
        holder.movieThumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieClickListener.onMovieClick(mMovieList.get(position));
            }
        });
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

    public interface OnMovieClickListener{
        void onMovieClick(Movie movie);
    }
}
