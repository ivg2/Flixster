package me.ivg2.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    //the base URL for loading images
    String imageBaseUrl;
    //the poster size to use when fetching images, appended to url
    String posterSize;

    public Config(JSONObject object) throws JSONException {
        //parse the image JSON object
        JSONObject images = object.getJSONObject("images");

        imageBaseUrl = images.getString("secure_base_url");

        //use the option at index 3 or the w342 if necessary
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        posterSize = posterSizeOptions.optString(3, "w342");

    }

    //helper method to construct picture URL by adding the three pieces
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
