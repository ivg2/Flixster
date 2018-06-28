package me.ivg2.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //base url for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";
    //tag for logging calls from this activity
    public final static String TAG = "MovieListActivity";

    AsyncHttpClient client;
    //the base URL for loading images
    String imageBaseUrl;
    //the poster size to use when fetching images, appended to url
    String posterSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        client = new AsyncHttpClient();

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
                    //parse the image JSON object
                    JSONObject images = response.getJSONObject("images");

                    imageBaseUrl = images.getString("secure_base_url");

                    //use the option at index 3 or the w342 if necessary
                    JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
                    posterSize = posterSizeOptions.optString(3, "w342");
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
