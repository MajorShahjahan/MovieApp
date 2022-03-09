package com.example.movieapp.Listeners;

import com.example.movieapp.models.DetailApiResponse;

public interface OnSearchDetailApiListener {

    void onResponse(DetailApiResponse response);
    void onError(String message);
}
