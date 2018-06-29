package me.ivg2.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.ivg2.flixster.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //initialize with list
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);

    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Movie movie = movies.get(i);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //TODO - set image using Glide
    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * Create the viewholder as a static inner class
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvTitle = itemView.findViewById(R.id.textView);
            tvOverview = itemView.findViewById(R.id.tvOverview);
        }
    }
}
