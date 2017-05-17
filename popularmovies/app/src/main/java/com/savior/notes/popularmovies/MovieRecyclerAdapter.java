package com.savior.notes.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savior.notes.popularmovies.data.MovieBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orlando on 5/10/2017.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final ListItemClickListener mOnClickListener;
    private List<MovieBean> movieList = new ArrayList<MovieBean>();
    private static final String TAG = MovieRecyclerAdapter.class.getSimpleName();
    private Context mContext;

    public MovieRecyclerAdapter(Context mContext , ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    public void swapCursor(List<MovieBean> newMovieList) {
        movieList = newMovieList;
        if (newMovieList != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_item, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view,mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)  {
        MovieBean movDesc = movieList.get(position);
        holder.listItemMovieView.setText(movDesc.getTitle());
        Picasso.with(mContext).load(NetworkUtils.getPosterImage(movDesc.getPosterPath()))
                .placeholder(R.drawable.ic_group_black)
                .error(R.drawable.ic_group_black)
                .into(holder.mImageView);
        holder.itemView.setTag(movDesc);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}