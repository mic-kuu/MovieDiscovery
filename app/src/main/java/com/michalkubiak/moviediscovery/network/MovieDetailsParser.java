package com.michalkubiak.moviediscovery.network;

import android.util.Log;
import android.widget.Toast;

import com.michalkubiak.moviediscovery.pojo.MovieDetails;
import com.michalkubiak.moviediscovery.pojo.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class designed to parse response with movie details from MovieDB API
 */
public class MovieDetailsParser {

    private String inputJson;
    private MovieDetails details;

    public static final String TAG_ADULT = "adult";
    public static final String TAG_BUDGET = "budget";
    public static final String TAG_GENRES = "genres";
    public static final String TAG_NAME = "name";
    public static final String TAG_ORIGINAL_TITLE = "original_title";
    public static final String TAG_OVERVIEW = "overview";
    public static final String TAG_STATUS = "status";
    public static final String TAG_TAGLINE = "tagline";
    public static final String TAG_VIDEO = "video";
    public static final String TAG_VOTE_AVERAGE = "vote_average";
    public static final String TAG_VOTE_COUNT = "vote_count";
    public static final String TAG_POSTER_PATH = "poster_path";

    public MovieDetailsParser(String inputJson) {

        this.inputJson = inputJson;
        details = new MovieDetails();

    }

    public boolean parse() {
        if (inputJson != null) {
            try {
                JSONObject movieDetailsObject = new JSONObject(inputJson);
                Log.v("PARSEREK", "przed pętlą");
                for (int i = 0; i < movieDetailsObject.length(); i++) {
                    Log.v("PARSEREK", "w pętli");
                    String adult = movieDetailsObject.getString(TAG_ADULT);
                    String budget = movieDetailsObject.getString(TAG_BUDGET);



                    JSONArray genresArray = movieDetailsObject.getJSONArray(TAG_GENRES);
                    Log.v("PARSEREK", "zlapal array");
                    ArrayList<String> genresList = new ArrayList<>();
                    Log.v("PARSEREK", "przed wewnetrzna petla");
                    for (int j = 0; j < genresArray.length(); j++) {

                        genresList.add(genresArray.getJSONObject(j).getString(TAG_NAME));

                        Log.v("PARSEREK", "dodal wew do listy");
                    }


                    String originalTitle = movieDetailsObject.getString(TAG_ORIGINAL_TITLE);

                    String overwiev = movieDetailsObject.getString(TAG_OVERVIEW);
                    String status = movieDetailsObject.getString(TAG_STATUS);
                    String tagline = movieDetailsObject.getString(TAG_TAGLINE);
                    String video = movieDetailsObject.getString(TAG_VIDEO);
                    String voteAverage = movieDetailsObject.getString(TAG_VOTE_AVERAGE);
                    String voteCount = movieDetailsObject.getString(TAG_VOTE_COUNT);
                    String posterPath = movieDetailsObject.getString(TAG_POSTER_PATH);



                    details.setAdult(Boolean.getBoolean(adult));
                    details.setBudget(budget);
                    details.setGenres(genresList);
                    details.setOrignalTitle(originalTitle);
                    details.setOverview(overwiev);
                    details.setStatus(status);
                    details.setTagline(tagline);
                    details.setVideo(Boolean.getBoolean(video));
                    details.setVoteAverage(voteAverage);
                    details.setVoteCount(voteCount);
                    details.setPosterPath(posterPath);
                }
                Log.v("PARSEREK", "pozytywny efekt");
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public MovieDetails getMovieDetails() {

        return details;
    }


}