package me.ivg2.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    //JSON values from API
    private String title;
    private String overview;
    private String posterPath; //only the path, not full url
    private String backdropPath;

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
