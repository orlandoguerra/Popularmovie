package com.savior.notes.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Orlando on 5/10/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView listItemMovieView;
    private ListItemClickListener mOnClickListener;
    public ImageView mImageView;
    private static final String TAG = MovieViewHolder.class.getSimpleName();

    public MovieViewHolder(View itemView, ListItemClickListener mOnClickListener) {
        super(itemView);
        this.mOnClickListener = mOnClickListener;
        listItemMovieView = (TextView) itemView.findViewById(R.id.tv_item_movie);
        mImageView = (ImageView) itemView.findViewById(R.id.imageMovie);
        itemView.setOnClickListener(this);
        mImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
        this.mOnClickListener.onListItemClick(clickedPosition);
    }
}
