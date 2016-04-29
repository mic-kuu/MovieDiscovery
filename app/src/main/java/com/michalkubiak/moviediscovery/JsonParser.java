package com.michalkubiak.moviediscovery;

import android.util.Log;

import com.michalkubiak.moviediscovery.pojo.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class designed to parse response from MovieDB API
 */
public class JsonParser {


    private JSONObject jsonObject;
    private JSONArray results;
    private String inputJson;

    private ArrayList<MovieItem> resultList;

    public static final String TAG_RESULTS = "results";
    public static final String TAG_POSTER_PATH = "poster_path";
    public static final String TAG_ID = "id";
    public static final String TAG_ORIGINAL_TITLE = "original_title";
    public static final String TAG_VOTE_AVERAGE = "vote_average";

    public JsonParser (String inputJson) {

        this.inputJson = inputJson;
        resultList = new ArrayList<>();

    }

    public boolean parse() {
        try {
            jsonObject = new JSONObject(inputJson);
            results = jsonObject.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);

                String posterPath = resultsJSONObject.getString(TAG_POSTER_PATH);
                String id = resultsJSONObject.getString(TAG_ID);
                String originalTitle = resultsJSONObject.getString(TAG_ORIGINAL_TITLE);
                String voteAverage = resultsJSONObject.getString(TAG_VOTE_AVERAGE);

                MovieItem movie = new MovieItem();

                movie.setId(id);
                movie.setPosterPath(posterPath);
                movie.setOrginalTitle(originalTitle);
                movie.setVoteAverage(voteAverage);

                resultList.add(movie);

            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<MovieItem> getResultList() {

        return resultList;
    }

    public ArrayList<String> getPosterThumbnails(){

        ArrayList<String> posterThumbnails = new ArrayList<>();

        for (MovieItem movie : resultList) {
            posterThumbnails.add(movie.getPosterPath());
        }

        return posterThumbnails;

    }



}