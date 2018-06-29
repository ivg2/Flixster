package me.ivg2.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.ivg2.flixster.models.Config;
import me.ivg2.flixster.models.Movie;

public class MovieListActivity extends AppCompatActivity {

    //base url for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";
    //tag for logging calls from this activity
    public final static String TAG = "MovieListActivity";

    AsyncHttpClient client;

    ArrayList<Movie> movies;

    RecyclerView rvMovies;
    MovieAdapter adapter;

    Config config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        client = new AsyncHttpClient();
        movies = new ArrayList<>();

        adapter = new MovieAdapter(movies);

        //resolve the recycler view and connect a layout manager
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        getConfiguration();
    }

    /**
     * get the configuration on app creation from the API
     */
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        //execute get request with an expected JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //get the values from the JSON response - both url and poster size
                try {
                    config = new Config(response);

                    Log.i(TAG, String.format("Loaded configuration with imageBaseUrl %s and " +
                            "posterSize %s", config.getImageBaseUrl(), config.getPosterSize()));

                    //pass config to adapter
                    adapter.setConfig(config);

                    getNowPlaying();

                } catch (JSONException e) {
                    String errorMessage = "Failed parsing configuration";
                    logError(errorMessage, e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String errorMessage = "Failed getting configuration";
                logError(errorMessage, throwable, true);
            }
        });
    }

    /**
     * get the list of currently playing movies from the API
     */
    private void getNowPlaying() {
        String url = API_BASE_URL + "/movie/now_playing";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //load the results from JSON to a movie object
                try {
                    JSONArray results = response.getJSONArray("results");

                    //iterate through results and append to movies arraylist
                    for(int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);

                        //notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }

                    Log.i(TAG, String.format("Loaded %s movies", results.length()));

                } catch (JSONException e) {
                    String errorMessage = "Failed to parse now playing movies";
                    logError(errorMessage, e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String errorMessage = "Failed to get data from now playing endpoint";
                logError(errorMessage, throwable, true);
            }
        });
    }

    //handles all errors and logs/alerts user
    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);

        //alert the user
        if (alertUser) {
            //show a toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
