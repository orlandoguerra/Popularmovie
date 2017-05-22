package com.savior.notes.popularmovies.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savior.notes.popularmovies.ListItemClickListener;
import com.savior.notes.popularmovies.MovieDetailsActivity;
import com.savior.notes.popularmovies.NetworkUtils;
import com.savior.notes.popularmovies.R;
import com.savior.notes.popularmovies.data.TrailerMovieBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/19/2017.
 */

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private final ListItemClickListener mOnClickListener;
    private List<TrailerMovieBean> trailerList = new ArrayList<TrailerMovieBean>();
    private static final String TAG = TrailerRecyclerAdapter.class.getSimpleName();
    private Context mContext;

    public TrailerRecyclerAdapter(Context mContext , ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    public void swapCursor(List<TrailerMovieBean> newTrailerList) {
        trailerList = newTrailerList;
        if (newTrailerList != null) {
            this.notifyDataSetChanged();
        }
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_item, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view,mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        TrailerMovieBean trailerDesc = trailerList.get(position);
        Log.i(this.getClass().getName(),NetworkUtils.getYouTubeThumbnails(trailerDesc.getKey()));
        Picasso.with(mContext).load(NetworkUtils.getYouTubeThumbnails(trailerDesc.getKey()))
                .placeholder(R.drawable.ic_group_black)
                .error(R.drawable.ic_group_black)
                .into(holder.mTrailerImageView);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}
