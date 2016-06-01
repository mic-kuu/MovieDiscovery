package com.michalkubiak.moviediscovery.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Simple implementation of AsyncTask that downloads JSON from MOVIEDB API,
 * handles errors and triggers its further processing
 */
public class RetrieveMovieDBAPI extends AsyncTask<Void, Void, String> {

    public AsyncResponse delegate = null;


    private String apiEndpoint = "testurl";
    private static final String TAG = RetrieveMovieDBAPI.class.getSimpleName();

    //Impementing it in ugly way
    //TODO: Find a better solution and refactor
    public static final int MOVIEGRID = 0;
    public static final int MOVIEDETAIL = 1;

    private int returnType = 0;

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (delegate != null){
            switch (returnType) {
                case MOVIEGRID:
                    delegate.processOutput(response);
                    break;
                case MOVIEDETAIL:
                    delegate.processDetailed(response);
                    break;
            }

        } else {
            Log.d(TAG, "There was an error in passing the data. Delegate is null");
        }

    }

    public void setAPIEndpoint (String apiEndpoint){
        this.apiEndpoint = apiEndpoint;
    }

    public void setResponseType (int responseType){
        returnType = responseType;
    }




}
