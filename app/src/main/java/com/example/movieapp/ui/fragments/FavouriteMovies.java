package com.example.movieapp.ui.fragments;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.Listeners.OnDeleteButtonClicked;
import com.example.movieapp.R;
import com.example.movieapp.adapters.FavouriteRecyclerAdapter;
import com.example.movieapp.db.DatabaseClient;
import com.example.movieapp.db.Movie;

import java.util.List;
import java.util.Objects;


public class FavouriteMovies extends Fragment implements OnDeleteButtonClicked {

    private RecyclerView favouriteRecyclerView;
    private FavouriteRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouriteRecyclerView = view.findViewById(R.id.recycler_View_favourite);
        getMovies();


    }

    private void getMovies() {

        class GetMovies extends AsyncTask<Void, Void, List<Movie>> {
            @Override
            protected List<Movie> doInBackground(Void... voids) {

                List<Movie> movieList = DatabaseClient
                        .getInstance(getActivity())
                        .getMovieDatabase()
                        .moviesDao()
                        .getAll();
                return movieList;
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);

                favouriteRecyclerView.setHasFixedSize(true);
                favouriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                adapter = new FavouriteRecyclerAdapter(getActivity(),movies, FavouriteMovies.this);
                favouriteRecyclerView.setAdapter(adapter);
            }
        }

        GetMovies gt = new GetMovies();
        gt.execute();
    }



    @Override
    public void onDeleteClick(Movie movie) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMovie(movie);

                getFragmentManager().beginTransaction().detach(FavouriteMovies.this).commit();
                getFragmentManager().beginTransaction().attach(FavouriteMovies.this).commit();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();


    }

    private void deleteMovie(Movie movie){
        class DeleteMovie extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getActivity()).getMovieDatabase()
                        .moviesDao()
                        .delete(movie);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        DeleteMovie dt = new DeleteMovie();
        dt.execute();
    }
}