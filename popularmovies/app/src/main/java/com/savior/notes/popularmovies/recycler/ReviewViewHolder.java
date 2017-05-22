package com.savior.notes.popularmovies.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.savior.notes.popularmovies.ListItemClickListener;
import com.savior.notes.popularmovies.R;

/**
 * Created by Orlando on 5/19/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView listItemReviewView;
    public TextView listItemReviewAuthorView;
    private static final String TAG = MovieViewHolder.class.getSimpleName();

    public ReviewViewHolder(View itemView) {
        super(itemView);
        listItemReviewView = (TextView) itemView.findViewById(R.id.tv_item_review);
        listItemReviewAuthorView = (TextView) itemView.findViewById(R.id.tv_item_review_auth);

    }
}
