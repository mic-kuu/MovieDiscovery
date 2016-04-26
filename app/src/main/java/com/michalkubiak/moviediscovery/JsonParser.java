package com.michalkubiak.moviediscovery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by michal on 21.04.16.
 */
public class JsonParser {

    private JSONObject jsonObject;
    private JSONArray results;
    private String inputJson;

    private ArrayList<HashMap<String, String>> resultList;

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

            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);

                String posterPath = resultsJSONObject.getString(TAG_POSTER_PATH);
                String id = resultsJSONObject.getString(TAG_ID);
                String orginal_title = resultsJSONObject.getString(TAG_ORIGINAL_TITLE);
                String vote_average = resultsJSONObject.getString(TAG_VOTE_AVERAGE);

                HashMap<String, String> result = new HashMap<>();

                result.put(TAG_POSTER_PATH, posterPath);
                result.put(TAG_ID, id);
                result.put(TAG_ORIGINAL_TITLE, orginal_title);
                result.put(TAG_VOTE_AVERAGE, vote_average);

                resultList.add(result);
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<HashMap<String, String>> getResultList () {

        return resultList;
    }

}