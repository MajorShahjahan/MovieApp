package com.example.movieapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Listeners.OnDeleteButtonClicked;
import com.example.movieapp.R;
import com.example.movieapp.db.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

   private Context context;
    private List<Movie> movieList;
    private OnDeleteButtonClicked mlistener;

    public FavouriteRecyclerAdapter(Context context, List<Movie> list, OnDeleteButtonClicked mlistener) {
        this.context = context;
        this.movieList = list;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favourite_movie_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Movie movie = movieList.get(position);
        holder.favourite_movie_name.setText(movie.getTitle());
        Picasso.get().load(movie.getImage()).into(holder.favourite_movie_poster);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mlistener.onDeleteClick(movie);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

class FavouriteViewHolder extends RecyclerView.ViewHolder{

    ImageView favourite_movie_poster;
    TextView favourite_movie_name;
    CardView favourite_home_container;
    ImageButton deleteButton;

    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);

        favourite_movie_poster = itemView.findViewById(R.id.favourite_movie_poster);
        favourite_movie_name = itemView.findViewById(R.id.favourite_movie_name);
        favourite_home_container = itemView.findViewById(R.id.favourite_home_container);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}
