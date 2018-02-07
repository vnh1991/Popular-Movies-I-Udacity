package com.udacity.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMoviesListing;
    private int numberOfColumns = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMoviesListing = (RecyclerView) findViewById(R.id.rv_movies);
        rvMoviesListing.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        new NetworkQueryTask().execute(generateUrl(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_top_rated:
                new NetworkQueryTask().execute(generateUrl(1));
                setTitle("Top Rated Movies");
                return true;
                //break;
            case R.id.menu_popular:
                new NetworkQueryTask().execute(generateUrl(0));
                setTitle("Popular Movies");
                return true;
                //break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public URL generateUrl(int val) {
        URL urlGenerated = null;
        switch (val) {
            case 0:
                Uri builtUriMostPopMovies = Uri.parse(NetworkUtils.MOST_POP_URL).buildUpon()
                        .build();
                try {
                    urlGenerated = new URL(builtUriMostPopMovies.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                Uri builtUriTopRatedMovies = Uri.parse(NetworkUtils.TOP_RATED_URL).buildUpon()
                        .build();
                try {
                    urlGenerated = new URL(builtUriTopRatedMovies.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
        return urlGenerated;
    }


    public class NetworkQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String jsonResponse = null;
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String networkResponse) {
            if (networkResponse != null && !networkResponse.equals("")) {

                try {
                    Gson _gson = new GsonBuilder().setPrettyPrinting().create();
                    ResponseBean response = _gson.fromJson(networkResponse, ResponseBean.class);

                    if (response.getResults() != null) {
                        displayNetworkResponse(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void displayNetworkResponse(ResponseBean responseData) {
        rvMoviesListing.setAdapter(new RVAdapter(this, responseData));
    }
}
