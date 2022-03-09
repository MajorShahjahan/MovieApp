package com.example.movieapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Listeners.OnSearchDetailApiListener;
import com.example.movieapp.R;
import com.example.movieapp.models.DetailApiResponse;
import com.example.movieapp.models.request.RequestManager;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView details_activity_movie_name, textview_movie_released, textview_movie_runtime,
            textview_movie_rating, movie_plot;
    ImageView imageview_details_activity;
    RequestManager manager;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        details_activity_movie_name = findViewById(R.id.details_activity_movie_name);
        textview_movie_released = findViewById(R.id.textview_movie_released);
        textview_movie_runtime = findViewById(R.id.textview_movie_runtime);
        textview_movie_rating = findViewById(R.id.textview_movie_rating);
        movie_plot = findViewById(R.id.movie_plot);
        imageview_details_activity = findViewById(R.id.imageview_details_activity);

        manager = new RequestManager(DetailsActivity.this);
        String movie_id = getIntent().getStringExtra("data");
        dialog = new ProgressDialog(DetailsActivity.this);
        dialog.setTitle("please wait...");
        dialog.show();
        manager.searchMoviesDetails(listener,movie_id);
    }

    private final OnSearchDetailApiListener listener = new OnSearchDetailApiListener() {
        @Override
        public void onResponse(DetailApiResponse response) {
            dialog.dismiss();
            if (response == null){
                Toast.makeText(DetailsActivity.this, "Could not find data", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);

        }

        @Override
        public void onError(String message) {

            dialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Error!", Toast.LENGTH_SHORT).show();

        }
    };

    private void showResult(DetailApiResponse response) {

        details_activity_movie_name.setText(response.getTitle());
        textview_movie_released.setText("Released :" + response.getYear());
        textview_movie_runtime.setText("Duration :" + response.getLength());
        textview_movie_rating.setText("Rating :" + response.getRating());
        movie_plot.setText("Description :" + response.getPlot());
        try {
            Picasso.get().load(response.getPoster()).into(imageview_details_activity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}