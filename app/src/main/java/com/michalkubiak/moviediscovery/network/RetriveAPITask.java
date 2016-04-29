package com.michalkubiak.moviediscovery.network;

import android.os.AsyncTask;
import android.util.Log;

import com.michalkubiak.moviediscovery.AsyncResponse;

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
public class RetriveAPITask extends AsyncTask<Void, Void, String> {

    public AsyncResponse delegate = null;

    private static final int PAGES_NO = 5;

    private static final String API_URL = APIcontract.getDiscoverMovieURL(PAGES_NO);
    private static final String TAG = RetriveAPITask.class.getSimpleName();

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(API_URL);
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
            delegate.processOutput(response);
        } else {
            Log.d(TAG, "There was an error in passing the data. Delegate is null");
        }

    }
}
