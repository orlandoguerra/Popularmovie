package com.savior.notes.popularmovies.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.savior.notes.popularmovies.ListItemClickListener;
import com.savior.notes.popularmovies.R;

/**
 * Created by Orlando on 5/19/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ListItemClickListener mOnClickListener;
    public ImageView mTrailerImageView;
    private static final String TAG = TrailerViewHolder.class.getSimpleName();

    public TrailerViewHolder(View itemView, ListItemClickListener mOnClickListener) {
        super(itemView);
        this.mOnClickListener = mOnClickListener;
        mTrailerImageView = (ImageView) itemView.findViewById(R.id.image_trailer);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int clickedPosition = getAdapterPosition();
        this.mOnClickListener.onListItemClick(clickedPosition);
    }
}
