package me.ivg2.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import me.ivg2.flixster.models.Config;
import me.ivg2.flixster.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    Config config;
    ArrayList<Movie> movies;
    Context context;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //initialize with list
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
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

        //build url for movie poster
        String imageUrl = config.getImageUrl(/*config.getPosterSize()*/"", movie.getPosterPath());

        RequestOptions options = RequestOptions.placeholderOf(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .fitCenter();

        //load the image using glide
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(holder.ivPosterImage);
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
