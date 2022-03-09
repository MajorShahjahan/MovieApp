package com.example.movieapp.Listeners;

import com.example.movieapp.models.SearchApiResponse;

public interface OnSearchApiListener {

     void onResponse(SearchApiResponse response);
     void onError(String message);

}
