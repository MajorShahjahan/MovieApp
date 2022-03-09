package com.example.movieapp.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;
    private MovieDatabase movieDatabase;

    public DatabaseClient(Context context) {
        this.context = context;
        movieDatabase = Room.databaseBuilder(context,MovieDatabase.class,"Movie Database").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

}
