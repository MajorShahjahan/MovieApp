package com.example.movieapp.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.ui.DetailsActivity;
import com.example.movieapp.Listeners.OnFavouriteButtonListener;
import com.example.movieapp.Listeners.OnMovieClickListener;
import com.example.movieapp.Listeners.OnSearchApiListener;
import com.example.movieapp.R;
import com.example.movieapp.db.DatabaseClient;
import com.example.movieapp.db.Movie;
import com.example.movieapp.models.request.RequestManager;
import com.example.movieapp.adapters.SearchRecyclerAdapter;
import com.example.movieapp.models.SearchApiResponse;


public class SearchMovies extends Fragment implements OnMovieClickListener , OnFavouriteButtonListener {


    SearchView searchView;
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recycler_View_Search);
        manager = new RequestManager(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Please wait...");
                progressDialog.show();
                manager.searchMovies(listener,query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnSearchApiListener listener = new OnSearchApiListener() {
        @Override
        public void onResponse(SearchApiResponse response) {

            progressDialog.dismiss();

            if (response==null){
                Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {

            progressDialog.dismiss();

            Toast.makeText(getActivity(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(SearchApiResponse response) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new SearchRecyclerAdapter(getActivity(),response.getTitles(),this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMovieClicked(String id) {

        startActivity(new Intent(getActivity(), DetailsActivity.class)
        .putExtra("data",id));

    }

    @Override
    public void onFavouriteButtonCLicked(String id, String title, String image) {

             saveMovie(id,title,image);

    }

    private void saveMovie(String id, String title, String image){

        class SaveMovie extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                Movie movie = new Movie();
                movie.setId(id);
                movie.setTitle(title);
                movie.setImage(image);

                DatabaseClient.getInstance(getActivity()).getMovieDatabase()
                        .moviesDao()
                        .insert(movie);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }
        SaveMovie st = new SaveMovie();
        st.execute();
    }
}