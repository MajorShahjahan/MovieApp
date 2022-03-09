package com.example.movieapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.Listeners.OnFavouriteButtonListener;
import com.example.movieapp.Listeners.OnMovieClickListener;
import com.example.movieapp.R;
import com.example.movieapp.models.SearchArrayObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    Context context;
    List<SearchArrayObject> list;
    OnMovieClickListener listener;
    OnFavouriteButtonListener mlistener;

    public SearchRecyclerAdapter(Context context, List<SearchArrayObject> list, OnMovieClickListener listener,OnFavouriteButtonListener mlitener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.mlistener = mlitener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_movies_list,parent,false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.movie_name.setText(list.get(position).getTitle());
        Picasso.get().load(list.get(position).getImage()).into(holder.movie_poster);
        holder.home_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClicked(list.get(position).getId());
            }
        });

        holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mlistener.onFavouriteButtonCLicked(list.get(position).getId()
                ,list.get(position).getTitle(),list.get(position).getImage());


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SearchViewHolder extends RecyclerView.ViewHolder {

    ImageView movie_poster;
    TextView movie_name;
    CardView home_container;
    ImageView favouriteButton;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        movie_poster = itemView.findViewById(R.id.movie_poster);
        movie_name = itemView.findViewById(R.id.movie_name);
        home_container = itemView.findViewById(R.id.home_container);
        favouriteButton = itemView.findViewById(R.id.favouriteButton);
    }
}
