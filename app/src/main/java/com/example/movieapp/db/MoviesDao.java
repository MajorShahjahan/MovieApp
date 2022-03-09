package com.example.movieapp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface MoviesDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Update
    void update(Movie movie);
}
