package com.aenima.android.popularmovies.core.movieadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aenima.android.popularmovies.R;
import com.aenima.android.popularmovies.core.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aenim on 13/03/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> mVideoList;

    public VideoAdapter(List<Video> videoList){
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Context context = holder.videoThumbNail.getContext();
        Video video = mVideoList.get(position);
        Log.d(this.getClass().getName(), video.getYoutubeUrl());
        Picasso.with(context).load(video.getYoutubeUrl()).into(holder.videoThumbNail);
        holder.videoName.setText(video.name);
        holder.sourceText.setText(context.getString(R.string.source_string) + " " + video.site);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail_iv)
        public ImageView videoThumbNail;

        @BindView(R.id.name_tv)
        public TextView videoName;
        @BindView(R.id.source_tv)
        public TextView sourceText;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
