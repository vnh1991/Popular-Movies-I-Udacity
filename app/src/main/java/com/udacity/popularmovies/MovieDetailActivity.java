package com.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private String movieTitle, moviePlot, movieDate, moviePosterPath, movieRating;
    private TextView lblTitle, lblPlot, lblDate, lblRating;
    private ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgPoster = findViewById(R.id.img_details_poster);
        lblTitle = findViewById(R.id.lbl_movie_title);
        lblDate = findViewById(R.id.lbl_movie_date);
        lblPlot = findViewById(R.id.lbl_movie_plot);
        lblRating = findViewById(R.id.lbl_vote_avg);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movieTitle = extras.getString("title");
            moviePlot = extras.getString("plot");
            movieDate = extras.getString("date");
            movieRating = extras.getString("rating") + "/10";
            moviePosterPath = extras.getString("poster");
        }

        if (moviePosterPath != null && moviePosterPath.length() > 0)
            Picasso.with(MovieDetailActivity.this).load(moviePosterPath).into(imgPoster);

        lblTitle.setText(movieTitle != null ? movieTitle : "");
        lblDate.setText(movieDate != null ? movieDate : "");
        lblPlot.setText(moviePlot != null ? moviePlot : "");
        lblRating.setText(movieRating != null ? movieRating : "");

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
