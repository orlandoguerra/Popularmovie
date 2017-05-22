package com.savior.notes.popularmovies.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savior.notes.popularmovies.ListItemClickListener;
import com.savior.notes.popularmovies.R;
import com.savior.notes.popularmovies.data.ReviewBean;
import com.savior.notes.popularmovies.data.TrailerMovieBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/19/2017.
 */

public class ReviewRecyclerAdapter  extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<ReviewBean> reviewList = new ArrayList<ReviewBean>();
    private static final String TAG = ReviewRecyclerAdapter.class.getSimpleName();
    private Context mContext;

    public ReviewRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void swapCursor(List<ReviewBean> newReviewList) {
        reviewList = newReviewList;
        if (newReviewList != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        ReviewBean reviewDesc = reviewList.get(position);
        holder.listItemReviewView.setText(reviewDesc.getContent());
        holder.listItemReviewAuthorView.setText(reviewDesc.getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
