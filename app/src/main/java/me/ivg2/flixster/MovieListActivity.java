package me.ivg2.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //base url for the API
    public final static String API_BASE_URL = "https://api.themovieb.org/3";
    //the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";
    //the API key
    public final static String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //tag for logging calls from this activity
    public final static String TAG = "MovieListActivity";

    AsyncHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        client = new AsyncHttpClient();
    }

    //get the configuration from the API
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, API_KEY);

        //execute get request with an expected JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
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
