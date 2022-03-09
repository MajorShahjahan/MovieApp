package com.example.movieapp.models.request;

import android.content.Context;
import android.widget.Toast;

import com.example.movieapp.Listeners.OnSearchApiListener;
import com.example.movieapp.Listeners.OnSearchDetailApiListener;
import com.example.movieapp.models.DetailApiResponse;
import com.example.movieapp.models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void searchMovies(OnSearchApiListener listener, String movie_name){

        getMovies getMovies = retrofit.create(RequestManager.getMovies.class);
        Call<SearchApiResponse> call = getMovies.callMovies(movie_name);

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Could not Fetch Data!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {

                listener.onError(t.getMessage());
            }
        });

    }
    public void searchMoviesDetails(OnSearchDetailApiListener listener, String movie_id){

        getMoviesDetails getMoviesDetails = retrofit.create(RequestManager.getMoviesDetails.class);
        Call<DetailApiResponse> call = getMoviesDetails.callMoviesDetails(movie_id);

        call.enqueue(new Callback<DetailApiResponse>() {
            @Override
            public void onResponse(Call<DetailApiResponse> call, Response<DetailApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Could not Fetch Data!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailApiResponse> call, Throwable t) {

                listener.onError(t.getMessage());
            }
        });

    }

    public interface getMovies{

        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: 19f07bc3f7mshcbfb589115db601p1d743fjsn43571dc223f2"
        })
        @GET("search/{movie_name}")
        Call<SearchApiResponse> callMovies( @Path("movie_name") String movie_name);

    }

    public interface getMoviesDetails{

        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: 19f07bc3f7mshcbfb589115db601p1d743fjsn43571dc223f2"
        })
        @GET("film/{movie_id}")
        Call<DetailApiResponse> callMoviesDetails(
                @Path("movie_id") String movie_id
        );

    }
}
